package ru.otus.zaikin.drive2.page.carpage;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.drive2.config.ApplicationContextProvider;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.page.BasePage;
import ru.otus.zaikin.drive2.repository.CarEntityRepository;
import ru.otus.zaikin.drive2.service.CarEntityService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class CarPagePO extends BasePage {
    private Query carBrendElement = new Query(By.cssSelector("a[data-ym-target='car2brand']"), driver);
    private Query carModelElement = new Query(By.cssSelector("a[data-ym-target='car2model']"), driver);
    private Query priceElement = new Query(By.cssSelector(".c-car-forsale__price > strong"), driver);
    private CarEntityRepository carEntityRepository = ((CarEntityService) ApplicationContextProvider.getInstance().getBean("carEntityService")).getRepository();


    public CarPagePO openCarById(long id) {
        log.trace("openCarById");
        CarEntitySet carEntitySet = carEntityRepository.findById(id).get();
        log.debug(carEntitySet);
        driver.get(carEntitySet.getHref());
        Wait wait = createAndGetWait(15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(priceElement.locator()));
        return this;
    }

    public CarPagePO readFieldsAndUpdateEntity(long id) {
        log.trace("readFieldsAndUpdateEntity");
        CarEntitySet entity = carEntityRepository.findById(id).get();
        String carBrend = carBrendElement.findWebElement().getText();
        String model = carModelElement.findWebElement().getText();
        String priceInString = priceElement.findWebElement().getText();
        double price = 0d;

        //RUR
        if (priceInString.contains("\u20BD")) {
            price = Double.parseDouble(priceInString.replace("\u20BD", "").replace("\u2009", ""));
            entity.setCurrency("RUR");
        }
        //USD
        if (priceInString.contains("$")) {
            price = Double.parseDouble(priceInString.replace("$", "").replace("\u2009", "").replace(",", ""));
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
                Date madeOnDate = new GregorianCalendar(Integer.parseInt(info.substring(matcher.start(), matcher.end())), Calendar.JANUARY, 1).getTime();
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

        carEntityRepository.save(entity);
        return this;
    }
}