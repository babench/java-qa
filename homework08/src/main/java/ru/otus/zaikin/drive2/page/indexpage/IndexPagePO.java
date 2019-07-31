package ru.otus.zaikin.drive2.page.indexpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.drive2.config.ApplicationContextProvider;
import ru.otus.zaikin.drive2.entity.CarBrendSet;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.page.BasePage;
import ru.otus.zaikin.drive2.repository.CarBrendRepository;
import ru.otus.zaikin.drive2.repository.CarEntityRepository;
import ru.otus.zaikin.drive2.service.CarBrendService;
import ru.otus.zaikin.drive2.service.CarEntityService;

import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@Log4j2
public class IndexPagePO extends BasePage {

    private By carCardsLocator = By.cssSelector(".c-car-card-sa");
    private By moreCarsButtonLocator = By.cssSelector("button[data-action='catalog.morecars']");
    private By brandsLocator = By.cssSelector("div.c-index-alt__brands > a");
    private CarBrendService carBrendService = (CarBrendService) ApplicationContextProvider.getInstance().getBean("carBrendService");
    private CarEntityService carEntityService = (CarEntityService) ApplicationContextProvider.getInstance().getBean("carEntityService");
    private CarBrendRepository carBrendRepository = carBrendService.getRepository();
    private CarEntityRepository carEntityRepository = carEntityService.getRepository();

    public IndexPagePO saveBrends() {
        List<WebElement> cars = driver.findElements(brandsLocator);
        cars.forEach(e -> {
            CarBrendSet carBrendSet = new CarBrendSet(e.getText());
            carBrendRepository.save(carBrendSet);
        });
        return this;
    }

    public void saveCarsOfBrend(String brend) {
        List<WebElement> cars = driver.findElements(brandsLocator);
        Optional<WebElement> optionalWebElement = cars.stream().filter(e -> e.getText().equalsIgnoreCase(brend)).findFirst();
        WebElement brendElement = optionalWebElement.orElseThrow(() -> new RuntimeException(brend + " not found"));
        brendElement.click();
        driver.findElement(By.cssSelector(".c-radiogroup__item[href='?sort=selling#filter']")).click();
        Wait wait = createAndGetWait();
        wait.until(d -> driver.findElement(By.cssSelector(".c-radiogroup__item.is-active")).getText().equalsIgnoreCase("В ПРОДАЖЕ"));
        List<WebElement> card;
        wait.until(d -> driver.findElement(By.cssSelector(".l-page-header ")).getText().contains("Продажа машин с историей"));
        card = driver.findElements(carCardsLocator);

        boolean moreCarsExists = driver.findElements(moreCarsButtonLocator).size() > 0;
        if (moreCarsExists) {
            int currentSize;
            do {
                currentSize = card.size();
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 5000)");
                wait.until(or(invisibilityOfElementLocated(moreCarsButtonLocator), numberOfElementsToBeMoreThan(carCardsLocator, card.size())));
                card = driver.findElements(carCardsLocator);
            } while (currentSize < card.size());
        }

        card.forEach(e -> {
            CarEntitySet entitySet = new CarEntitySet();
            entitySet.setHref(e.findElement(By.cssSelector("a")).getAttribute("href"));
            carEntityRepository.save(entitySet);
        });
    }
}