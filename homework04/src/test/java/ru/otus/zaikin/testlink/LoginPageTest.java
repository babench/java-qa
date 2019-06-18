package ru.otus.zaikin.testlink;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoginPageTest extends DriverBase {

    private LoginPagePO loginPagePO;
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        loginPagePO = new LoginPagePO();
    }

    @Test
    public void shouldLogin() {
        driver.get("http://192.168.99.100/login.php");

        loginPagePO.enterUsername("admin")
                .enterPassword("admin")
                .andLogin();

        assertThat(driver.findElements(By.cssSelector("frame")).get(0).getAttribute("name")).isEqualTo("titlebar");
    }

    @Test
    public void shouldLoginWithProfile() {
        loginPagePO.openSite();
        loginPagePO.enterUsername(loginPagePO.prop.getProperty("app.username"))
                .enterPassword(loginPagePO.prop.getProperty("app.userpassword"))
                .andLogin();

        assertThat(driver.findElements(By.cssSelector("frame")).get(0).getAttribute("name")).isEqualTo("titlebar");
    }
}