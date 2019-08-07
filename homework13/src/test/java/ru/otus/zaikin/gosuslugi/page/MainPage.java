package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Главная")
public class MainPage extends BasePage {

    @FindBy(linkText = "Проверка штрафов")
    @Name("Проверка штрафов")
    SelenideElement gibddPenaltyPage;

    @FindBy(linkText = "Получение загранпаспорта")
    @Name("Получение загранпаспорта")
    SelenideElement foreignPassportPage;

    @FindBy(linkText = "Запись к врачу")
    @Name("Запись к врачу")
    SelenideElement toDoctorPage;

    @FindBy(css = "a.button-base--enter")
    @Name("Вход в личный кабинет")
    SelenideElement loginPage;

    @FindBy(linkText = "Родители и дети")
    @Name("Родители и дети")
    SelenideElement newBabyPage;

    @FindBy(linkText = "Справка об отсутствии судимости")
    @Name("Справка об отсутствии судимости")
    SelenideElement noCriminalityPage;
}
