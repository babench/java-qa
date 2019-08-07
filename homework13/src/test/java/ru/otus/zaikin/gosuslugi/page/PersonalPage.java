package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Личная страница")
public class PersonalPage extends BasePage {
    @Name("ФИО")
    @FindBy(css = "div[data-ng-if='!$root.orgType']")
    SelenideElement fio;

    @Name("ИНН")
    @FindBy(css = "div[data-ng-if='$root.user.inn']")
    SelenideElement inn;
}
