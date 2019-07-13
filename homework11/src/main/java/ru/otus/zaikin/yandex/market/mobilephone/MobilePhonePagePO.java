package ru.otus.zaikin.yandex.market.mobilephone;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.yandex.market.BasePage;


@Log4j2
public class MobilePhonePagePO extends BasePage {

    private Query header = new Query(By.cssSelector(".headline__header"), driver);

    public String getHeaderText() {
        return header.findWebElement().getText();
    }

}