package ru.otus.zaikin.yandex.market.electronica;

import com.lazerycode.selenium.util.Query;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;

import java.util.Optional;

import static ru.otus.zaikin.framework.AllureUtils.saveScreenShotForAllure;

@Log4j2
@Epic("Yandex")
@Feature("Market")
@Story("Electronika")
public class ElectronicaPagePO extends BasePage {

    private Query header = new Query(By.cssSelector(".headline__header-title"), driver);

    public String getHeaderText() {
        return header.findWebElement().getText();
    }

    @Step("open sub item")
    public ElectronicaPagePO openSubItem(String subItem) {
        log.trace("openSubItem:" + subItem);
        Optional<WebElement> webElementOptional = driver.findElements(By.cssSelector("div[data-zone-name='link'] > a")).stream().filter(p -> p.getText().equals(subItem)).findFirst();
        WebElement e = webElementOptional.orElseThrow(() -> new RuntimeException("sub item not found " + subItem));
        e.click();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(header.locator(), subItem));
        waitPageIsLoaded();
        saveScreenShotForAllure();
        return this;
    }
}