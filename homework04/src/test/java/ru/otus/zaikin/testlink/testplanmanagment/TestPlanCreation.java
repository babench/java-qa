package ru.otus.zaikin.testlink.testplanmanagment;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

public class TestPlanCreation extends DriverBase {
    static final String TESTLINK_URL = "http://192.168.99.100/login.php";
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        openSite();
    }

    private void openSite() {
        System.out.println("TestPlanCreation.openSite");
        driver.get(TESTLINK_URL);
        System.out.println();
    }

    @Test
    public void shouldLogin() {
        System.out.println("TestPlanCreation.inputUserName");
        loginToSite();
        System.out.println("assert place");
    }

    @Test
    public void shouldOpenTestPlan() {
        loginToSite();
        driver.switchTo().frame(1);
        driver.findElement(By.linkText("Test Plan Management")).click();
        System.out.println("here");

    }

    private void loginToSite() {
        driver.findElement(By.id("tl_login")).clear();
        driver.findElement(By.id("tl_login")).sendKeys("admin");
        driver.findElement(By.id("tl_password")).clear();
        driver.findElement(By.id("tl_password")).sendKeys("admin");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }

}