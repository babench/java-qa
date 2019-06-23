package ru.otus.zaikin.yandex.market.compare;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;

import java.util.Optional;


@Log4j2
public class ComparePagePO extends BasePage {
    private static final By COMPARE_ROW_NAME = By.cssSelector(".n-compare-row-name");
    private static final By COMPARE_SPINNER = By.cssSelector(".n-compare-table__spinner");

    private Query toCompare = new Query(By.cssSelector(".n-user-lists_type_compare_in-list_no"), driver);
    private Query compareBtn = new Query(By.cssSelector(".header2-menu__item_type_compare"), driver);
    private Query showAllButton = new Query(By.cssSelector(".n-compare-show-controls__all"), driver);
    private Query showDiffButton = new Query(By.cssSelector(".n-compare-show-controls__diff"), driver);

    public ComparePagePO addToComparing(int i) {
        log.debug("ComparePagePO.addToComparing");
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(toCompare.locator(), i));
        Actions actions = new Actions(driver);
        actions.moveToElement(toCompare.findWebElement()).build().perform();
        driver.executeScript("arguments[0].click();", toCompare.findWebElement());
        waitPageIsLoaded();
        return this;
    }

    public ComparePagePO addToComparing(String model) {
        log.debug("ComparePagePO.addToComparing");
        log.debug(model);
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(toCompare.locator(), 20));

        Optional<WebElement> optionalWebElement = driver.findElements(By.cssSelector(".n-user-lists_type_compare_in-list_no")).stream().filter(e -> e.getAttribute("data-bem").contains(model)).findFirst();
        WebElement anyMode = optionalWebElement.orElseThrow(() -> {
            throw new RuntimeException("not found");
        });

        Actions actions = new Actions(driver);
        actions.moveToElement(anyMode).build().perform();
        driver.executeScript("arguments[0].click();", anyMode);
        waitPageIsLoaded();
        return this;
    }

    public ComparePagePO performComparing() {
        log.debug("ComparePagePO.performComparing");
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.elementToBeClickable(compareBtn.locator()));
        Actions actions = new Actions(driver);
        actions.moveToElement(compareBtn.findWebElement()).click().build().perform();
        wait.until(dr -> driver.findElement(By.cssSelector(".n-page-compare")));
        return this;
    }

    public ComparePagePO comparingShowAll() {
        log.debug("ComparePagePO.comparingShowAll");
        showAllButton.findWebElement().click();
        waitPageIsLoaded();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(COMPARE_SPINNER)));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(COMPARE_ROW_NAME, 5));
        return this;
    }

    public ComparePagePO comparingShowDiff() {
        log.debug("ComparePagePO.comparingShowDiff");
        showDiffButton.findWebElement().click();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(COMPARE_SPINNER)));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(COMPARE_ROW_NAME, 1));
        waitPageIsLoaded();
        return this;
    }

    public boolean isDeltaContains(String category) {
        long count = driver.findElements(By.cssSelector(".n-compare-row-name")).stream().filter(p -> p.getText().contains(category)).count();
        return count > 0L;
    }
}