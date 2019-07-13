package ru.otus.zaikin.yandex.market;

import com.lazerycode.selenium.util.Query;
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

@Log4j2
public abstract class BasePage {

    protected RemoteWebDriver driver;


    public BasePage() {
        log.debug("BasePage.BasePage");
        try {
            driver = DriverBase.getDriver();
        } catch (MalformedURLException e) {
            log.error("error", e);
        }
    }

    public void openSite() {
        log.debug("BasePage.openSite");
        driver.get(YandexMarketConfig.mainUrl);
        //driver.manage().addCookie(new Cookie("spravka", "dD0xNTYxMjk2OTYyO2k9ODUuMTQxLjgwLjEzOTt1PTE1NjEyOTY5NjIyMDYwMzg1MTM7aD1hZWUyMzZjYmZhN2VmNzNkNWYzMGMxMzhiYjVjODQwYQ=="));
        //driver.manage().addCookie(new Cookie("zaikin", "test"));
        log.debug("here");
        driver.get(YandexMarketConfig.url);
    }

    protected WebDriverWait createAndGetWait(int delay) {
        return new WebDriverWait(driver, delay);
    }

    protected WebDriverWait createAndGetWait() {
        return new WebDriverWait(driver, 15);
    }

    protected void waitPageIsLoaded() {
        log.debug("BasePage.waitPageIsLoaded");
        WebDriverWait wait = createAndGetWait();
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".spin2"))));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".spin2_progress_yes"))));
    }

    public BasePage setFilter(FilterEnum filterEnum, String params) {
        log.debug("BasePage.setFilter");
        log.debug(filterEnum.getLegend());
        Wait wait = createAndGetWait();
        By filterItem = By.cssSelector("input[name='" + filterEnum.getLegend() + " " + params + "']");
        wait.until(dr -> driver.findElement(filterItem));
        //WebElement element = driver.findElement(filterItem);

        while (!driver.findElement(filterItem).isSelected()) {
            WebElement element = driver.findElement(filterItem);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().build().perform();
        }
        return this;
    }

    public void searchResultsWait(Wait wait) {
        log.debug("BasePage.searchResultsWait");
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style"));
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
        wait.until(dr -> !driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style"));
        log.debug(driver.findElement(By.cssSelector(".n-pager-more")).getAttribute("style").isEmpty());
    }

    public BasePage sortBy(ResultSortEnum resultSortEnum, SortDirection sortDirection) {
        log.debug("BasePage.sortBy");
        Optional<WebElement> webElementOptional = driver.findElements(By.cssSelector(".n-filter-sorter")).stream().filter(e -> e.getText().equals(resultSortEnum.getCriteria())).findFirst();
        WebElement noSortType = webElementOptional.orElseThrow(() -> new RuntimeException("no sort type " + resultSortEnum.getCriteria()));
        while (!noSortType.getAttribute("class").contains("filter-sorter_sort_" + sortDirection.getValue())) {
            Actions actions = new Actions(driver);
            actions.moveToElement(noSortType).click().build().perform();
        }
        Wait wait = createAndGetWait();
        searchResultsWait(wait);
        waitPageIsLoaded();
        return this;
    }

    public String getPopupInformerText() {
        log.debug("IndexPagePO.getPopupInformerText");
        Wait wait = createAndGetWait();
        Query popup = new Query(By.cssSelector(".popup-informer__title"), driver);
        wait.until(presenceOfElementLocated(popup.locator()));
        wait.until(ExpectedConditions.visibilityOf(popup.findWebElement()));
        wait.until(dr -> !popup.findWebElement().getText().isEmpty());
        return popup.findWebElement().getText();
    }

    public BasePage closeInformer() {
        log.debug("IndexPagePO.closeInformer");
        Wait wait = createAndGetWait();
        Query closeButton = new Query(By.cssSelector(".popup-informer__close"), driver);
        wait.until(ExpectedConditions.elementToBeClickable(closeButton.locator()));
        Actions actions = new Actions(driver);
        actions.moveToElement(closeButton.findWebElement()).click().build().perform();
        waitPageIsLoaded();
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
        return this;
    }
}