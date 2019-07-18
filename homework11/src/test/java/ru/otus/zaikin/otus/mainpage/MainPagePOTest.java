package ru.otus.zaikin.otus.mainpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.otus.exception.LoginException;
import ru.otus.zaikin.otus.loginpage.LoginPagePO;
import ru.otus.zaikin.otus.personalpage.PersonalPagePO;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class MainPagePOTest extends DriverBase {

    private WebDriver driver;
    private MainPagePO mainPagePO;
    private LoginPagePO loginPagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        mainPagePO = new MainPagePO();
    }

    @Test(description = "should login")
    public void shouldLogin() {
        login();
        log.debug(driver.findElement(By.cssSelector(".ic-blog-default-avatar")).getAttribute("style"));
    }

    private void login() {
        log.debug("BasePageTest.shouldOpen");
        loginPagePO = mainPagePO.openSite().openLoginPage();
        String userId = System.getProperty("otus.user.id");
        String userPassword = System.getProperty("otus.user.password");
        loginPagePO.login(userId, userPassword);
    }

    @Test(description = "should login and open personal page")
    public void shouldLoginAndOpenPersonalPage() {
        login();
        log.debug(driver.findElement(By.cssSelector(".ic-blog-default-avatar")).getAttribute("style"));
        PersonalPagePO personalPagePO = mainPagePO.openPersonalPage();
        assertThat(driver.findElement(By.id("id_email")).getAttribute("value")).isEqualToIgnoringCase(System.getProperty("otus.user.id"));
    }

    @Test(expectedExceptions = LoginException.class, description = "should not login with wrong User/Password")
    public void shouldNotLogin() {
        loginPagePO = mainPagePO.openSite().openLoginPage();
        loginPagePO.login("test", "test");
    }
}