package ru.otus.zaikin.google;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPagePO extends BasePage {

    private Query usernameField = new Query(By.cssSelector("#identifierId"), driver);
    private Query userNameNext = new Query(By.cssSelector("#identifierNext"), driver);
    private Query passwordField = new Query(By.cssSelector("#password > div.aCsJod.oJeWuf > div > div.Xb9hP > input"), driver);
    private Query passwordNext = new Query(By.cssSelector("#passwordNext"), driver);

    public LoginPagePO login(String userId, String userPassword) {
        Wait wait = new WebDriverWait(driver, 5);
        wait.until(dr -> driver.findElement(usernameField.locator()));
        usernameField.findWebElement().sendKeys(userId);
        userNameNext.findWebElement().click();

        wait.until(ExpectedConditions.elementToBeClickable(passwordField.locator()));
        passwordField.findWebElement().sendKeys(userPassword);
        passwordNext.findWebElement().click();

        return this;
    }
}