package ru.otus.zaikin.yandex.market.mobilephone;

import com.lazerycode.selenium.util.Query;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.yandex.market.BasePage;


@Log4j2
@Epic("Yandex")
@Feature("Market")
@Story("MobilePhone")
public class MobilePhonePagePO extends BasePage {

    private Query header = new Query(By.cssSelector(".headline__header"), driver);

    @Step("get Header text")
    public String getHeaderText() {
        return header.findWebElement().getText();
    }

}