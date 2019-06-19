package ru.otus.zaikin.testlink;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.framework.AppProperties;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.Properties;

@Log4j2
public abstract class BasePage {

    Properties prop;
    protected RemoteWebDriver driver;

    public BasePage() {
        log.trace("BasePage.BasePage");
        log.debug("load properties");
        prop = AppProperties.getInstance().getProperties();
        log.debug("getDriver");

        try {
            driver = DriverBase.getDriver();
        } catch (MalformedURLException e) {
            log.error("error", e);
        }

    }

    void openSite() {
        driver.get(prop.getProperty("app.url"));
    }

    public void openSiteAndLogin() {
        LoginPagePO loginPagePO = new LoginPagePO();
        loginPagePO.openSite();
        loginPagePO.enterUsername(loginPagePO.prop.getProperty("app.username"))
                .enterPassword(loginPagePO.prop.getProperty("app.userpassword"))
                .andLogin();
    }

    public String readFeedBack() {
        return driver.findElement(By.className("user_feedback")).getText();
    }

    public void switchToMainFrame() {
        driver.switchTo().defaultContent();
        driver.switchTo().frame(Frames.mainframe.toString());
    }

    public void switchToFrame(Frames frames) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//frame[@name='" + frames.name() + "']")));
        driver.switchTo().frame(frames.name());
    }

    public void backToFrame(Frames workframe) {
        switchToMainFrame();
        switchToFrame(workframe);
    }

    public void sendKeysToContents(int index, String value) {
        Wait wait = new WebDriverWait(driver, 2);
        wait.until(d -> ExpectedConditions.elementToBeClickable(getCkeIframe(index)));

        driver.switchTo().frame(getCkeIframe(index));
        driver.findElement(By.cssSelector("body")).sendKeys(value);
    }

    public WebElement getCkeIframe(int index) {
        return driver.findElement(By.id("cke_" + index + "_contents")).findElement(By.cssSelector("iframe"));
    }
}