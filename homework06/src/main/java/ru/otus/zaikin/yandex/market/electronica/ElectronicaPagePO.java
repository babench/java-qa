package ru.otus.zaikin.yandex.market.electronica;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;

import java.util.Optional;


@Log4j2
public class ElectronicaPagePO extends BasePage {

    private Query header = new Query(By.cssSelector(".headline__header"), driver);

    public String getHeaderText() {
        return header.findWebElement().getText();
    }

    public ElectronicaPagePO openSubItem(String subItem) {
        log.debug("IndexPagePO.openSubItem");
        Optional<WebElement> webElementOptional = driver.findElements(By.cssSelector("div[data-zone-name='link'] > a")).stream().filter(p -> p.getText().equals(subItem)).findFirst();
        WebElement e = webElementOptional.orElseThrow(() -> new RuntimeException("sub item not found " + subItem));
        e.click();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".headline__header"), subItem));
        waitPageIsLoaded();
        return this;
    }
}