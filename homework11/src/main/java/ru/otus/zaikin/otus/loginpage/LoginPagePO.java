package ru.otus.zaikin.otus.loginpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.exception.LoginException;

import java.util.concurrent.TimeUnit;

@Log4j2
public class LoginPagePO extends BasePage {
    private By loginForm = By.cssSelector(".new-log-reg__form.js-login");
    private By usernameField = By.name("email");
    private By passwordField = By.name("password");
    private By loginButton = By.cssSelector("button");
    private By errorLocator = By.cssSelector(".new-input-error");
    private By avatarLocator = By.cssSelector(".ic-blog-default-avatar");

    public LoginPagePO login(String userId, String userPassword) {
        Wait wait = new WebDriverWait(driver, 5);
        wait.until(dr -> driver.findElement(loginForm));
        driver.findElement(loginForm).findElement(usernameField).sendKeys(userId);
        driver.findElement(loginForm).findElement(passwordField).sendKeys(userPassword);
        driver.findElement(loginForm).findElement(loginButton).click();
        log.debug("wait results");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(errorLocator),
                ExpectedConditions.presenceOfElementLocated(avatarLocator)));
        log.debug("wait passed");
        String errorOfLogin = driver.findElement(errorLocator).getText();
        if (errorOfLogin != null && !errorOfLogin.isEmpty()) throw new LoginException(errorOfLogin);
        return this;
    }
}