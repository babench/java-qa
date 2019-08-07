package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Рождение ребёнка")
public class BabyNewPage extends BasePage {
    @FindBy(css = "h1.b-content__title")
    @Name("Заголовок")
    SelenideElement pageHeader;
}