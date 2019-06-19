package ru.otus.zaikin.framework;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static ru.otus.zaikin.framework.DriverType.FIREFOX;

@Log4j2
public class DriverFactory {

    private RemoteWebDriver webDriver;
    private DriverType selectedDriverType;

    public DriverFactory() {
        DriverType driverType = FIREFOX;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            log.error("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            log.error("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public RemoteWebDriver getDriver() throws MalformedURLException {
        if (null == webDriver) {
            instantiateWebDriver(selectedDriverType);
        }

        return webDriver;
    }

    public void quitDriver() {
        if (null != webDriver) {
            /*TODO: add holdBrowserOpenOnFails*/
            webDriver.quit();
            webDriver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
        log.debug("DriverFactory.instantiateWebDriver");
        log.debug("Selected Browser: " + selectedDriverType);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        webDriver = driverType.getWebDriverObject(desiredCapabilities);
    }
}