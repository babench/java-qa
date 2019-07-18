package ru.otus.zaikin.otus.mainpage;


import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.loginpage.LoginPagePO;

public class MainPagePO extends BasePage {

    private final HeaderBlock headerBlock;

    public MainPagePO() {
        headerBlock = new HeaderBlock();
    }

    public LoginPagePO openLoginPage() {
        headerBlock.getLoginButton().click();
        return new LoginPagePO();
    }
}