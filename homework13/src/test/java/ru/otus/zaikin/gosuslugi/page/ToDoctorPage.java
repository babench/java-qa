package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Запись на прием к врачу")
public class ToDoctorPage extends BasePage {
    @FindBy(css = "h1.ng-binding")
    @Name("Заголовок")
    SelenideElement pageHeader;
}
