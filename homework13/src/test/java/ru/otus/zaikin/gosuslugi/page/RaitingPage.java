package ru.otus.zaikin.gosuslugi.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.otus.zaikin.framework.annotations.Name;
import ru.otus.zaikin.framework.common.BasePage;

@Name("Рейтинг электронных услуг")
public class RaitingPage extends BasePage {

    @Name("Page Title")
    @FindBy(css = "h1.h1")
    SelenideElement pageTitle;

    @Name("Top-1-in-header")
    @FindBy(css = "#content > div.ng-scope > div > div > div > div.top-services-wrap.ng-scope > div.top-service-1 > div.top-service-border-wrap > div.top-service-info-wrap > div > a")
    SelenideElement top1_header;

    @Name("Top-1-in-list")
    @FindBy(css = "#content > div.ng-scope > div > div > div > div.popular-services-wrap.ng-scope > div:nth-child(2) > div.popular-service-info > div > div.popular-service-name > a")
    SelenideElement top1_list;

    @Name("Top-1-score")
    @FindBy(css = "#content > div.ng-scope > div > div > div > div.top-services-wrap.ng-scope > div.top-service-1 > div.top-service-border-wrap > div.top-service > div.top-service-rate.popular-services-numbers.normal.ng-binding")
    SelenideElement top1_score;

    @Name("Top-2-score")
    @FindBy(css = "#content > div.ng-scope > div > div > div > div.top-services-wrap.ng-scope > div.top-service-2 > div.top-service-border-wrap > div.top-service > div.top-service-rate.popular-services-numbers.normal.ng-binding")
    SelenideElement top2_score;
}
