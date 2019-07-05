package ru.otus.zaikin.drive2.indexpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.drive2.BasePage;
import ru.otus.zaikin.drive2.entity.CarBrendSet;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Log4j2
public class IndexPagePO extends BasePage {
    private HibernateDao dao;

    public IndexPagePO(HibernateDao dao) {
        this.dao = dao;
    }

    public IndexPagePO saveBrends() {
        List<WebElement> cars = driver.findElements(By.cssSelector("div.c-index-alt__brands > div > a"));
        cars.forEach(e -> {
            CarBrendSet carBrendSet = new CarBrendSet(e.getText());
            dao.create(carBrendSet);
        });
        return this;
    }

    public void saveCarsOfBrend(String brend) {
        List<WebElement> cars = driver.findElements(By.cssSelector("div.c-index-alt__brands > div > a"));
        Optional<WebElement> optionalWebElement = cars.stream().filter(e -> e.getText().equalsIgnoreCase(brend)).findFirst();
        WebElement brendElement = optionalWebElement.orElseThrow(() -> new RuntimeException(brend + " not found"));
        brendElement.click();
        driver.findElement(By.cssSelector(".c-radiogroup__item[href='?sort=selling#filter']")).click();
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(d -> driver.findElement(By.cssSelector(".c-radiogroup__item.is-active")).getText().equalsIgnoreCase("В ПРОДАЖЕ"));
        List<WebElement> card = new ArrayList<>();
        int currentSize = -1;
        wait.until(d -> driver.findElement(By.cssSelector(".l-page-header ")).getText().contains("Продажа машин с историей"));

        while (currentSize < card.size()) {
            currentSize = card.size();
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,5000)");
            try {
                wait.until(and(invisibilityOfElementLocated(By.cssSelector("button.[data-action='catalog.morecards']")), (numberOfElementsToBeMoreThan(By.cssSelector(".c-car-card-sa"), currentSize))));

            } catch (Exception e) {
                e.printStackTrace();
            }
            card = driver.findElements(By.cssSelector(".c-car-card-sa"));
        }

        card.forEach(e -> {
            CarEntitySet entitySet = new CarEntitySet();
            entitySet.setHref(e.findElement(By.cssSelector("a")).getAttribute("href"));
            dao.create(entitySet);
        });
    }
}