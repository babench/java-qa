package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Справка об отсутствии судимости")
public class NoCriminalRecordsPage extends BasePage {
    @FindBy(css = "h1.ng-binding")
    @Name("Заголовок")
    SelenideElement pageHeader;

}
