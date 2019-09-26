package ru.otus.zaikin;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    private AndroidDriver<MobileElement> driver;

    @BeforeEach
    void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.APP, new File("./src/test/resources/Calc.apk").getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
        capabilities.setCapability(MobileCapabilityType.UDID, "192.168.81.101:5555");
        driver = new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void shouldPlus() {
        WebElement firstArg = driver.findElementById("digit_7");
        firstArg.click();
        WebElement actionButton = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"+\")");
        actionButton.click();
        WebElement secondArg = driver.findElementByXPath(".//*[@text='5']");
        secondArg.click();
        WebElement equals = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").description(\"equals\")");
        equals.click();
        WebElement result = driver.findElementById("result_final");
        assertEquals("12", result.getText());
    }

    @Test
    void shouldMultiply() {
        WebElement firstArg = driver.findElementById("digit_2");
        firstArg.click();
        WebElement actionButton = driver.findElementByAndroidUIAutomator("new UiSelector().text(\"×\")");
        actionButton.click();
        WebElement secondArg = driver.findElementByXPath(".//*[@text='7']");
        secondArg.click();
        WebElement equals = driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.widget.Button\").description(\"equals\")");
        equals.click();
        WebElement result = driver.findElementById("result_final");
        assertEquals("14", result.getText());
    }
}