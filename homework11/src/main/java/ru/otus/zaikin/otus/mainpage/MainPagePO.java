package ru.otus.zaikin.otus.mainpage;


import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.OtusConfig;
import ru.otus.zaikin.otus.loginpage.LoginPagePO;
import ru.otus.zaikin.otus.personalpage.PersonalPagePO;

public class MainPagePO extends BasePage {

    private final HeaderBlock headerBlock;

    public MainPagePO() {
        headerBlock = new HeaderBlock();
    }

    public LoginPagePO openLoginPage() {
        headerBlock.getLoginButton().click();
        return new LoginPagePO();
    }

    public PersonalPagePO openPersonalPage() {
        driver.get(OtusConfig.URL + OtusConfig.PERSONAL_PAGE);
        return new PersonalPagePO();
    }

}