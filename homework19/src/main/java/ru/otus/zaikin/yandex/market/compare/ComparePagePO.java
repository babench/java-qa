package ru.otus.zaikin.yandex.market.compare;

import com.lazerycode.selenium.util.Query;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;

import java.util.Optional;

import static ru.otus.zaikin.framework.AllureUtils.saveScreenShotForAllure;

@Log4j2

@Epic("Yandex")
@Feature("Market")
@Story("Compare")
public class ComparePagePO extends BasePage {
    private static final By COMPARE_ROW_NAME = By.cssSelector(".n-compare-row-name");
    private static final By COMPARE_SPINNER = By.cssSelector(".n-compare-table__spinner");

    private Query toCompare = new Query(By.cssSelector(".n-user-lists_type_compare_in-list_no"), driver);
    private Query compareBtn = new Query(By.cssSelector(".header2-menu__item_type_compare"), driver);
    private Query showAllButton = new Query(By.cssSelector(".n-compare-show-controls__all"), driver);
    private Query showDiffButton = new Query(By.cssSelector(".n-compare-show-controls__diff"), driver);

    @Step("add to comparing")
    public ComparePagePO addToComparing(int i) {
        log.trace("addToComparing");
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(toCompare.locator(), i));
        Actions actions = new Actions(driver);
        actions.moveToElement(toCompare.findWebElement()).build().perform();
        driver.executeScript("arguments[0].click();", toCompare.findWebElement());
        waitPageIsLoaded();
        return this;
    }

    @Step("add to comparing")
    public ComparePagePO addToComparing(String model) {
        log.trace("addToComparing");
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
        saveScreenShotForAllure();
        return this;
    }

    @Step("perform compare")
    public ComparePagePO performComparing() {
        log.trace("performComparing");
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.elementToBeClickable(compareBtn.locator()));
        Actions actions = new Actions(driver);
        actions.moveToElement(compareBtn.findWebElement()).click().build().perform();
        wait.until(dr -> driver.findElement(By.cssSelector(".n-page-compare")));
        saveScreenShotForAllure();
        return this;
    }

    @Step("show all parameters")
    public ComparePagePO comparingShowAll() {
        log.trace("comparingShowAll");
        showAllButton.findWebElement().click();
        waitPageIsLoaded();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(COMPARE_SPINNER)));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(COMPARE_ROW_NAME, 5));
        saveScreenShotForAllure();
        return this;
    }

    @Step("show diff only")
    public ComparePagePO comparingShowDiff() {
        log.trace("comparingShowDiff");
        showDiffButton.findWebElement().click();
        Wait wait = createAndGetWait();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(COMPARE_SPINNER)));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(COMPARE_ROW_NAME, 1));
        waitPageIsLoaded();
        saveScreenShotForAllure();
        return this;
    }

    @Step("verify delta")
    public boolean isDeltaContains(String category) {
        long count = driver.findElements(By.cssSelector(".n-compare-row-name")).stream().filter(p -> p.getText().contains(category)).count();
        return count > 0L;
    }
}