package ru.otus.zaikin.selenium;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.time.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class WaitPlayTest extends DriverBase {
    public static final String URL = "http://localhost:8080/collection_with_delays.html";
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        driver.get(URL);
    }

    @Test
    public void shouldOpenPage() {
        assertThat(driver.findElement(By.cssSelector("h1")).getText()).isEqualTo("Elements will appear soon");
    }

    @Test
    public void errorCase() {
        driver.manage().timeouts().implicitlyWait(0, SECONDS);
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe1BySleep() {
        driver.manage().timeouts().implicitlyWait(0, SECONDS);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe2ByImplicitWaitNotWorks() {
        driver.manage().timeouts().implicitlyWait(3, SECONDS);
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe3ByImplicitWaitWorks() {
        driver.manage().timeouts().implicitlyWait(15, SECONDS);
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe4ByExplicitWait() {
        driver.manage().timeouts().implicitlyWait(0, SECONDS);
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("elem_5")));
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe5WaitRace() {
        /*should it fail or not?*/
        driver.manage().timeouts().implicitlyWait(2, SECONDS);
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("elem_5")));
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

    @Test
    public void fixMe6FluentWait() {
        driver.manage().timeouts().implicitlyWait(0, SECONDS);
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(300)).ignoring(NoSuchElementException.class);
        //Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(300));
        WebElement elem_5 = wait.until(d -> d.findElement(By.id("elem_5")));
        assertThat(elem_5.getText()).isEqualTo("Element #5");
    }

    @Test
    public void fixMe7ByExplicitWait() {
        driver.manage().timeouts().implicitlyWait(0, SECONDS);
        Wait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div[id^='elem_'"),2));
        String elem_5 = driver.findElement(By.id("elem_5")).getText();
        assertThat(elem_5).isEqualTo("Element #5");
    }

}