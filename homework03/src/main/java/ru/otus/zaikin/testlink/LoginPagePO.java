package ru.otus.zaikin.testlink;

import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.By;
import ru.otus.zaikin.testlink.indexpage.IndexPagePO;

public class LoginPagePO extends BasePage {

    private Query usernameField = new Query(By.id("tl_login"), driver);
    private Query passwordField = new Query(By.id("tl_password"), driver);
    private Query loginButton = new Query(By.xpath("//*[@id='login']/div[3]/input"), driver);

    LoginPagePO enterUsername(String username) {
        usernameField.findWebElement().sendKeys(username);
        return this;
    }

    LoginPagePO enterPassword(String password) {
        passwordField.findWebElement().sendKeys(password);
        return this;
    }


    LoginPagePO andLogin() {
        loginButton.findWebElement().click();
        return this;
    }

    public void andFailLogin() {
        loginButton.findWebElement().click();
    }

    public IndexPagePO andSuccessfullyLogin() {
        loginButton.findWebElement().click();
        return new IndexPagePO();
    }

    public IndexPagePO successfully() {
        return new IndexPagePO();
    }
}