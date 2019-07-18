package ru.otus.zaikin.otus.loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.otus.BasePage;


public class LoginPagePO extends BasePage {

    private By usernameField = By.cssSelector("div.new-input-placeholder-box.new-input-placeholder-box_hide > input[name='email']");
    private By passwordField = By.name("Password");
    private By loginButton = By.cssSelector(".button");

    public LoginPagePO login(String userId, String userPassword) {
        Wait wait = new WebDriverWait(driver, 5);
        wait.until(dr -> driver.findElement(usernameField));
        driver.findElement(usernameField).sendKeys(userId);
        driver.findElement(passwordField).sendKeys(userPassword);
        driver.findElement(loginButton).click();
        return this;
    }
}