package ru.otus.zaikin.bootstrap;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

@Log4j2
public class AlertGame extends DriverBase {
    public static final String URL = "https://ng-bootstrap.github.io/#/components/alert/examples\n";
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        driver.get(URL);
    }

    @Test
    public void shouldOpenPage() {
        WebElement button = driver.findElements(By.cssSelector(".btn.btn-primary")).get(1);
        button.click();
        WebElement alert = driver.findElement(By.xpath("//ngb-alert[@type='success']"));
        log.debug(alert.getText());
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.stalenessOf(alert));
        button.click();
        WebElement alertNew = driver.findElement(By.xpath("//ngb-alert[@type='success']"));
        log.debug(alertNew.getText());
        log.debug("end");
    }
}