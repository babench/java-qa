package ru.otus.zaikin.otus.mainpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.otus.loginpage.LoginPagePO;

import java.net.MalformedURLException;

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

    @Test
    public void shouldLogin() {
        log.debug("BasePageTest.shouldOpen");
        loginPagePO = mainPagePO.openSite().openLoginPage();
        String userId = System.getProperty("otus.user.id");
        String userPassword = System.getProperty("otus.user.password");;
        loginPagePO.login(userId,userPassword);
    }
}