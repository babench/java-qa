package ru.otus.zaikin.drive2.carpage;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.drive2.BasePage;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class CarPagePO extends BasePage {
    private HibernateDao dao;
    private Query carBrendElement = new Query(By.cssSelector("a[data-ym-target='car2brand']"), driver);
    private Query carModelElement = new Query(By.cssSelector("a[data-ym-target='car2model']"), driver);
    private Query priceElement = new Query(By.cssSelector(".c-car-forsale__price > strong"), driver);


    public CarPagePO(HibernateDao dao) {
        this.dao = dao;
    }

    public CarPagePO openCarById(long id) {
        System.out.println("CarPagePO.openCarById");
        CarEntitySet entity = dao.get(id, CarEntitySet.class);
        log.debug(entity);
        assertThat(entity).isNotNull();
        driver.get(entity.getHref());
        Wait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceElement.locator()));
        return this;
    }

    public CarPagePO readFieldsAndUpdateEntity(long id) {
        System.out.println("CarPagePO.readFieldsAndUpdateEntity");
        CarEntitySet entity = dao.get(id, CarEntitySet.class);
        String carBrend = carBrendElement.findWebElement().getText();
        String model = carModelElement.findWebElement().getText();
        String priceInString = priceElement.findWebElement().getText();
        double price = 0d;

        //RUR
        if (priceInString.contains("\u20BD")) {
            price = Double.valueOf(priceInString.replace("\u20BD", "").replace("\u2009", ""));
            entity.setCurrency("RUR");
        }
        //USD
        if (priceInString.contains("$")) {
            price = Double.valueOf(priceInString.replace("$", "").replace("\u2009", "").replace(",", ""));
            entity.setCurrency("USD");
        }

        List<WebElement> infoElements = driver.findElements(By.cssSelector(".c-car-forsale__info> li"));
        entity.setCarBrend(carBrend);
        entity.setModel(model);
        entity.setPrice(price);

        //made year
        Optional<WebElement> optionalWebElementMadeOn = infoElements.stream().filter(e -> e.getText().contains("года выпуска")).findFirst();
        WebElement madeOnElement = optionalWebElementMadeOn.orElse(null);
        if (madeOnElement != null) {
            String info = madeOnElement.getText();
            Pattern pattern = Pattern.compile("\\d{4}");
            Matcher matcher = pattern.matcher(info);
            while (matcher.find()) {
                Date madeOnDate = new GregorianCalendar(Integer.valueOf(info.substring(matcher.start(), matcher.end())), Calendar.JANUARY, 1).getTime();
                entity.setIssueDate(madeOnDate);
                break;
            }
        }

        //engine block
        Optional<WebElement> optionalWebElementPowerLS = infoElements.stream().filter(e -> e.getText().startsWith("Двигатель")).findFirst();
        WebElement powerLSElement = optionalWebElementPowerLS.orElse(null);
        if (powerLSElement != null) {
            String info = powerLSElement.getText();
            Pattern pattern = Pattern.compile("\\d{2,}\\sл.с.");
            Matcher matcher = pattern.matcher(info);
            while (matcher.find()) {
                String lowadPower = info.substring(matcher.start(), matcher.end()).split(" ")[0];
                entity.setEnginePower(Integer.parseInt(lowadPower));
                break;
            }
            entity.setEngineVolume(Double.valueOf(info.split(" ")[1]));
            entity.setEngineType(info.split(" ")[2]);
        }

        // update record
        dao.update(entity);
        return this;
    }
}