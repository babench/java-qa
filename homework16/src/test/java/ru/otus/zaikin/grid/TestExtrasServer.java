package ru.otus.zaikin.grid;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.URL;

public class TestExtrasServer {
    @Test(enabled = false, description = "test for Extras Server")
    public void testAlert() throws Exception {
        System.setProperty("webdriver.chrome.driver", "d:/temp/grid/chromedriver.exe");
        ChromeOptions cap = new ChromeOptions();
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"), cap);

        driver.get("https://htmlweb.ru/java/js1.php");
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.findElement(By.cssSelector("code"));
        driver.quit();
    }
}
