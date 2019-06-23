package ru.otus.zaikin.yandex.market.indexpage;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;
import ru.otus.zaikin.yandex.market.ResultSortEnum;
import ru.otus.zaikin.yandex.market.SortDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


@Log4j2
public class IndexPagePO extends BasePage {

    private Query logoLocator = new Query(By.cssSelector(".logo_part_market"), driver);
    private List<NavMenuItem> navMenuItems = new ArrayList<>();

    @Override
    public void openSite() {
        log.debug("IndexPagePO.openSite");
        super.openSite();
        createAndGetWait().until(ExpectedConditions.visibilityOf(logoLocator.findWebElement()));
        waitPageIsLoaded();
    }

    public IndexPagePO readTabs() {
        log.debug("IndexPagePO.readTabs");
        createAndGetWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".n-w-tab.n-w-tab_type_navigation-menu a")));
        driver.findElements(By.cssSelector(".n-w-tab.n-w-tab_type_navigation-menu a")).forEach(e -> navMenuItems.add(new NavMenuItem(e.getText(), e.getAttribute("href"))));
        log.debug("number of submenu " + navMenuItems.size());
        log.debug("IndexPagePO.readTabs:end");
        return this;
    }

    int getNavMenuSize() {
        return navMenuItems.size();
    }

    NavMenuItem getNavMenuItem(int index) {
        return navMenuItems.get(index);
    }

    String getLogoText() {
        return logoLocator.findWebElement().getText();
    }

    public IndexPagePO openTab(IndexTab indexTab) {
        log.debug("IndexPagePO.openTab");
        return this;
    }

    public IndexPagePO openTab(String caption) {
        log.debug("IndexPagePO.openTab");
        log.debug(caption);
        Optional<NavMenuItem> navMenuItemOptional = navMenuItems.stream().filter(e -> e.getCaption().equals(caption)).findFirst();
        NavMenuItem item = navMenuItemOptional.orElseThrow(() -> new RuntimeException("Menu not found " + caption));
        log.debug(item.getHref());
        driver.get(item.getHref());
        Wait wait = createAndGetWait(60);
        //here could ne captha! Please input it manually
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[data-apiary-widget-name='@MarketNode/CatalogHeader']"))));
        /*todo: add wait*/
        log.debug("here");
        return this;
    }

}