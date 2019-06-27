package ru.otus.zaikin.testlink.testplanmanagment;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

@Log4j2
public class TestPlanManagementSpagetti extends DriverBase {
    static final String TESTLINK_URL = "http://192.168.99.100/login.php";
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        openSite();
    }

    private TestPlanManagementSpagetti openSite() {
        driver.get(TESTLINK_URL);
        return this;
    }

    @Test
    public void shouldLogin() {
        openSite().inputLogin("admin").inputPassword("admin").performLogin();
        System.out.println("assert placeholder");
    }

    @Test
    public void shouldCreateTestPlan(String planName) {
        openSite().inputLogin("admin").inputPassword("admin").performLogin();
        driver.switchTo().frame("mainframe");
        driver.findElement(By.linkText("Test Plan Management")).click();
        driver.findElement(By.name("create_testplan")).click();
        driver.findElement(By.name("testplan_name")).click();
        driver.findElement(By.name("testplan_name")).clear();
        driver.findElement(By.name("testplan_name")).sendKeys(planName);
        driver.findElement(By.name("is_public")).click();
        driver.findElement(By.name("do_create")).click();
    }

    private TestPlanManagementSpagetti inputLogin(String login) {
        driver.findElement(By.id("tl_login")).clear();
        driver.findElement(By.id("tl_login")).sendKeys(login);
        return this;
    }

    private TestPlanManagementSpagetti inputPassword(String password) {
        driver.findElement(By.id("tl_password")).clear();
        driver.findElement(By.id("tl_password")).sendKeys(password);
        return this;

    }

    private TestPlanManagementSpagetti performLogin() {
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        return this;
    }
}