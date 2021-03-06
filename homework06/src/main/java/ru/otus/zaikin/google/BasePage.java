package ru.otus.zaikin.google;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.google.youtube.YoutubeConfig;

import java.net.MalformedURLException;

@Log4j2
public abstract class BasePage {
    protected RemoteWebDriver driver;

    public BasePage() {
        log.debug("BasePage.BasePage");
        try {
            driver = DriverBase.getDriver();
        } catch (MalformedURLException e) {
            log.error("error", e);
        }
    }

    public void openSite() {
        log.debug("BasePage.openSite");
        driver.get(YoutubeConfig.url);
    }

    protected WebDriverWait createAndGetWait(int delay) {
        return new WebDriverWait(driver, delay);
    }

    protected WebDriverWait createAndGetWait() {
        return new WebDriverWait(driver, 15);
    }


}