package ru.otus.zaikin.habr;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.security.Key;

@Log4j2
public class HabrHubPageTestIT extends DriverBase {
    private RemoteWebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        driver.get(HabrConfig.URL_HUBS);
    }

    @Test(description = "should open IT Systems")
    public void shouldOpenItSystems() {
        By locator = By.cssSelector("#hubs .list-snippet__title-link");
        String hubName = "Тестирование IT-систем";
        driver.findElement(By.cssSelector("#hubs_suggest")).sendKeys(hubName);
        driver.findElement(By.cssSelector("#hubs_suggest")).sendKeys(Keys.ENTER);
        new WebDriverWait(driver, 10).until(ExpectedConditions.textToBe(locator, hubName));
    }
}