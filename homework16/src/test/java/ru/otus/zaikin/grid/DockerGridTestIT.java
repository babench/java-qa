package ru.otus.zaikin.grid;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

@Log4j2
public class DockerGridTestIT extends DriverBase {
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
    }

    @Test(description = "should open in Docker Grid")
    public void testAlert() throws Exception {
        driver.get("https://htmlweb.ru/java/js1.php");
        log.debug("site opened");
        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.findElement(By.cssSelector("code"));
        log.debug("test passed");
    }
}
