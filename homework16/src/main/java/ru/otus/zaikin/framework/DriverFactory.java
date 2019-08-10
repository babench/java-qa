package ru.otus.zaikin.framework;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static ru.otus.zaikin.framework.DriverType.FIREFOX;

@Log4j2
@Epic("Framework")
@Feature("Driver")
@Story("Factory")
public class DriverFactory {

    private RemoteWebDriver webDriver;
    private DriverType selectedDriverType;
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");


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
        if (null == webDriver) instantiateWebDriver(selectedDriverType);
        return webDriver;
    }

    public void quitDriver() {
        if (null != webDriver) {
            webDriver.quit();
            webDriver = null;
        }
    }

    @Step("instantiate driver")
    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
        log.trace("instantiateWebDriver");
        log.debug("Local Operating System: " + operatingSystem);
        log.debug("Local Architecture: " + systemArchitecture);
        log.debug("Selected Browser: " + selectedDriverType);
        log.debug("Connecting to Selenium Grid: " + useRemoteWebDriver);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (useRemoteWebDriver) {
            log.debug("remote run");
            boolean useSauceLab = Boolean.getBoolean("sauceType");
            if (useSauceLab) {
                String browserName = System.getProperty("browserName") == null ? "chrome" : System.getProperty("browserName");
                String browserVersion = System.getProperty("browserVersion") == null ? "73.0" : System.getProperty("browserVersion");
                String platformName = System.getProperty("platformName") == null ? "macOS 10.14" : System.getProperty("platformName");
                MutableCapabilities capabilities = new MutableCapabilities();
                if (browserName.equals("chrome")) {
                    ChromeOptions caps = new ChromeOptions();
                    caps.setExperimentalOption("w3c", true);
                    capabilities = caps;
                } else if (browserName.equals("firefox")) {
                    capabilities = new FirefoxOptions();
                }

                capabilities.setCapability("browserVersion", browserVersion);
                capabilities.setCapability("platformName", platformName);

                String username = System.getProperty("SAUCE_USERNAME");
                String accesskey = System.getProperty("SAUCE_ACCESS_KEY");
                MutableCapabilities sauceOptions = new MutableCapabilities();
                sauceOptions.setCapability("username", username);
                sauceOptions.setCapability("accessKey", accesskey);
                sauceOptions.setCapability("seleniumVersion", "3.141.59");
                sauceOptions.setCapability("build", "parallel-TestNG-single-browser-demo");
                //Assign the Sauce Options to the base capabilities
                capabilities.setCapability("sauce:options", sauceOptions);

                //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
                String SAUCE_REMOTE_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
                webDriver = new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), capabilities);
                log.debug("sauce driver created");
            } else {
                log.debug("simple remote");
                URL seleniumGridURL = new URL(System.getProperty("gridURL"));
                String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
                String desiredPlatform = System.getProperty("desiredPlatform");

                if (null != desiredPlatform && !desiredPlatform.isEmpty())
                    desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));

                if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty())
                    desiredCapabilities.setVersion(desiredBrowserVersion);

                desiredCapabilities.setBrowserName(selectedDriverType.toString().toLowerCase());
                webDriver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
                log.debug(webDriver.getSessionId());
            }
        } else {
            log.debug("local run");
            webDriver = driverType.getWebDriverObject(desiredCapabilities);
        }
        webDriver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("driverWait", "5")), TimeUnit.SECONDS);
    }
}