package ru.otus.zaikin.otus.loginpage;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.otus.BasePage;


public class LoginPagePO extends BasePage {
    private By loginForm = By.cssSelector(".new-log-reg__form.js-login");
    private By usernameField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button");

    public LoginPagePO login(String userId, String userPassword) {
        Wait wait = new WebDriverWait(driver, 5);
        wait.until(dr -> driver.findElement(loginForm));
        driver.findElement(loginForm).findElement(usernameField).sendKeys(userId);
        driver.findElement(loginForm).findElement(passwordField).sendKeys(userPassword);
        driver.findElement(loginForm).findElement(loginButton).click();
        return this;
    }
}