package ru.otus.zaikin.drive2.page;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.framework.AppProperties;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

@Log4j2
public abstract class BasePage {
    protected RemoteWebDriver driver;

    public BasePage() {
        log.trace("BasePage");
        try {
            driver = DriverBase.getDriver();
        } catch (MalformedURLException e) {
            log.error("error", e);
        }
    }

    public void openSite() {
        log.trace("openSite");
        driver.get(AppProperties.getInstance().getProperty("app.url"));
    }

    protected WebDriverWait createAndGetWait(int delay) {
        return new WebDriverWait(driver, delay);
    }

    protected WebDriverWait createAndGetWait() {
        return new WebDriverWait(driver, Integer.parseInt(AppProperties.getInstance().getProperty("app.timeout")));
    }
}