package ru.otus.zaikin.framework;

import lombok.extern.log4j.Log4j2;
import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
public class DriverBase {

    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());
    private static ThreadLocal<DriverFactory> driverThread;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverThread = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory webDriverThread = new DriverFactory();
                webDriverThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };
    }

    public static RemoteWebDriver getBrowserMobProxyEnabledDriver() throws MalformedURLException {
        return driverThread.get().getDriver(true);
    }

    public static RemoteWebDriver getDriver() throws MalformedURLException {
        return driverThread.get().getDriver();
    }

    public static BrowserMobProxy getBrowserMobProxy() {
        return driverThread.get().getBrowserMobProxy();
    }

    @AfterMethod(alwaysRun = true)
    public static void clearCookies() {
        try {
            getDriver().manage().deleteAllCookies();
        } catch (Exception ex) {
            log.error("Unable to delete cookies: " + ex);
        }
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (DriverFactory webDriverThread : webDriverThreadPool) {
            webDriverThread.quitDriver();
        }
    }
}