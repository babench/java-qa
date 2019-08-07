package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.annotations.Optional;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Вход в личный кабинет")
public class LoginPage extends BasePage {

    @FindBy(id = "mobileOrEmail")
    @Name("userName")
    SelenideElement userName;

    @FindBy(id = "password")
    @Name("userPassword")
    SelenideElement userPassword;

    @FindBy(id = "loginByPwdButton")
    @Name("loginButton")
    SelenideElement loginButton;

    @FindBy(css = "div.field-error")
    @Optional
    @Name("field error")
    SelenideElement fieldError;
}