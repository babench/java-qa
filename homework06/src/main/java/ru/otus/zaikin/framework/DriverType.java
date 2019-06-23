package ru.otus.zaikin.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;

public enum DriverType implements DriverSetup {

    FIREFOX {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            FirefoxOptions options = new FirefoxOptions();
            options.merge(capabilities);
            options.setHeadless(HEADLESS);
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver(options);
        }
    },
    CHROME {
        public RemoteWebDriver getWebDriverObject(DesiredCapabilities capabilities) {
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", false);
           /* chromePreferences.put("excludeSwitches",
                    ImmutableList.of("disable-component-update", "disable-default-apps", "ignore-certificate-errors", "safebrowsing-disable-download-protection",
                            "safebrowsing-disable-auto-update", "disable-client-side-phishing-detection"));

*/
            ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            options.setHeadless(HEADLESS);

            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-plugins-discovery");
            options.addArguments("--always-authorize-plugins");
            options.addArguments("--allow-outdated-plugins");
            options.addArguments("--allow-file-access-from-files");
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--disable-translate");
            options.addArguments("--mute-audio");

            options.addArguments("--disable-notifications");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-infobars");


            options.setExperimentalOption("prefs", chromePreferences);
//            options.addArguments("user-data-dir=c:/temp/Chrome");
            //   options.addArguments("--profile-directory=Название_папки_с_нужным_профилем");


            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(options);
        }
    };

    public final static boolean HEADLESS = Boolean.getBoolean("headless");
}