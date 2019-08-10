package ru.otus.zaikin.yandex.market;

import com.lazerycode.selenium.util.Query;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static ru.otus.zaikin.framework.AllureUtils.saveScreenShotForAllure;

@Log4j2
@Epic("Yandex")
@Feature("Market")
public abstract class BasePage {

    protected RemoteWebDriver driver;


    public BasePage() {
        log.trace("BasePage");
        try {
            driver = DriverBase.getDriver();
        } catch (MalformedURLException e) {
            log.error("error", e);
        }
    }

    @Step("open site")
    public void openSite() {
        log.trace("openSite");
        driver.get(YandexMarketConfig.mainUrl);
        saveScreenShotForAllure();
        driver.get(YandexMarketConfig.url);
        saveScreenShotForAllure();
    }


    protected WebDriverWait createAndGetWait(int delay) {
        return new WebDriverWait(driver, delay);
    }

    protected WebDriverWait createAndGetWait() {
        return new WebDriverWait(driver, 15);
    }

    protected void waitPageIsLoaded() {
        log.trace("waitPageIsLoaded");
        WebDriverWait wait = createAndGetWait();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".spin2"))));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".spin2_progress_yes"))));
    }

    @Step("set filter")
    public BasePage setFilter(FilterEnum filterEnum, String params) {
        log.trace("setFilter");
        saveScreenShotForAllure();
        log.debug(filterEnum.getLegend());
        Wait wait = createAndGetWait();
        By filterItem = By.cssSelector("input[name='" + filterEnum.getLegend() + " " + params + "']");
        wait.until(dr -> driver.findElement(filterItem));
        //WebElement element = driver.findElement(filterItem);
        int attemps = 10;
        while (!driver.findElement(filterItem).isSelected() && attemps > 0) {
            WebElement element = driver.findElement(filterItem);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
            attemps--;
        }
        if(attemps==0) throw new RuntimeException("not able to move and click");
        saveScreenShotForAllure();
        return this;
    }

    public void searchResultsWait(Wait wait) {
        log.trace("searchResultsWait");
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style"));
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
        wait.until(dr -> !driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style"));
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
        saveScreenShotForAllure();
    }

    @Step("sort by")
    public BasePage sortBy(ResultSortEnum resultSortEnum, SortDirection sortDirection) {
        log.trace("sortBy");
        Optional<WebElement> webElementOptional = driver.findElements(By.cssSelector(".n-filter-sorter")).stream().filter(e -> e.getText().equals(resultSortEnum.getCriteria())).findFirst();
        WebElement noSortType = webElementOptional.orElseThrow(() -> new RuntimeException("no sort type " + resultSortEnum.getCriteria()));
        int attemps = 10;
        while (!noSortType.getAttribute("class").contains("filter-sorter_sort_" + sortDirection.getValue())
        && attemps>0) {
            Actions actions = new Actions(driver);
            actions.moveToElement(noSortType).click().build().perform();
            attemps--;
        }
        if(attemps==0) throw new RuntimeException("not able to move and click");
        Wait wait = createAndGetWait();
        searchResultsWait(wait);
        waitPageIsLoaded();
        saveScreenShotForAllure();
        return this;
    }

    @Step("get popup informer text")
    public String getPopupInformerText() {
        log.debug("IndexPagePO.getPopupInformerText");
        Wait wait = createAndGetWait();
        Query popup = new Query(By.cssSelector(".popup-informer__title"), driver);
        wait.until(presenceOfElementLocated(popup.locator()));
        wait.until(ExpectedConditions.visibilityOf(popup.findWebElement()));
        wait.until(dr -> !popup.findWebElement().getText().isEmpty());
        saveScreenShotForAllure();
        return popup.findWebElement().getText();
    }

    @Step("close informer")
    public BasePage closeInformer() {
        log.debug("IndexPagePO.closeInformer");
        Wait wait = createAndGetWait();
        Query closeButton = new Query(By.cssSelector(".popup-informer__close"), driver);
        wait.until(ExpectedConditions.elementToBeClickable(closeButton.locator()));
        Actions actions = new Actions(driver);
        actions.moveToElement(closeButton.findWebElement()).click().build().perform();
        waitPageIsLoaded();
        saveScreenShotForAllure();
        return this;
    }

    public BasePage clearFilter(String filterType) {
        Wait wait = createAndGetWait();
        wait.until(dr -> driver.findElement(By.cssSelector("input[name*='" + filterType + "']")));
        List<WebElement> list = driver.findElements(By.cssSelector("input[name*='" + filterType + "']"));
        for (WebElement webElement : list) {
            if (webElement.isSelected()) {
                while (webElement.isSelected()) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(webElement).click().build().perform();
                    searchResultsWait(wait);
                    waitPageIsLoaded();
                }
            }
        }
        saveScreenShotForAllure();
        return this;
    }
}