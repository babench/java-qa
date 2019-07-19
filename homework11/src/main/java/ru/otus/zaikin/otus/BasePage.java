package ru.otus.zaikin.otus;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.otus.mainpage.MainPagePO;

@Log4j2
public abstract class BasePage {
    protected RemoteWebDriver driver;

    public BasePage() {
        log.debug("BasePage.BasePage");
        driver = DriverBase.getDriver();
    }

    public MainPagePO openSite() {
        log.debug("BasePage.openSite");
        driver.get(OtusConfig.URL);
        return new MainPagePO();
    }

    protected WebDriverWait createAndGetWait(int delay) {
        return new WebDriverWait(driver, delay);
    }

    protected WebDriverWait createAndGetWait() {
        return new WebDriverWait(driver, 15);
    }
}