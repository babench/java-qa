package ru.otus.zaikin.framework;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.net.MalformedURLException;

import static ru.otus.zaikin.framework.DriverBase.getDriver;

public class AllureUtils {
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenShotForAllure() {
        try {
            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
