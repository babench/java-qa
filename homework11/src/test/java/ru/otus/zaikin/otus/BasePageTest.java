package ru.otus.zaikin.otus;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.otus.mainpage.MainPagePO;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class BasePageTest extends DriverBase {

    private WebDriver driver;
    private MainPagePO mainPagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        mainPagePO = new MainPagePO();
    }

    @Test
    public void shouldOpen() {
        log.debug("BasePageTest.shouldOpen");
        mainPagePO.openSite();
        assertThat(driver.findElement(By.cssSelector(".header2__logo-img")).isDisplayed()).isTrue();
    }

    @Test
    public void shouldGetMainPage() {
        MainPagePO mainPagePO = this.mainPagePO.openSite();
        assertThat(mainPagePO).isNotNull();
    }
}