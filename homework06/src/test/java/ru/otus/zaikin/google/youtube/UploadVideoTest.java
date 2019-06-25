package ru.otus.zaikin.google.youtube;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.google.LoginPagePO;

import java.net.MalformedURLException;

/***
 * Онлайн задание при занятии 7 "Работа с нативными окнами браузера: Alert, Prompt, Confirm, iFrame, Tabs, BasicAuth"
 * Надо загрузить видео файл c:\test.mp4 на youtube
 */
@Log4j2
public class UploadVideoTest extends DriverBase {

    private LoginPagePO loginPagePO;
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        driver.get(YoutubeConfig.url);
        loginPagePO = new LoginPagePO();
    }

    @Test
    public void shouldLoginAndUpload() {
        log.debug("UploadVideoTest.shouldLoginAndUpload");
        loginPagePO.login(System.getProperty("gmailuser", "null"), System.getProperty("gmailpassword", "null"));
        UploadVideoPO uploadVideoPO = new UploadVideoPO();
        uploadVideoPO.upload("c:\\test.mp4");
        log.debug("assert placeholder");
    }
}