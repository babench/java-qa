package ru.otus.zaikin.otus.mainpage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.otus.zaikin.otus.BasePage;

public class HeaderBlock extends BasePage {
    private final static By loginButton = By.cssSelector("button.header2__auth.js-open-modal");

    public WebElement getLoginButton() {
        return driver.findElement(loginButton);
    }

}
