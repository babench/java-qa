package ru.otus.zaikin.yandex.market.indexpage;

import com.lazerycode.selenium.util.Query;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import ru.otus.zaikin.yandex.market.BasePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.otus.zaikin.framework.AllureUtils.saveScreenShotForAllure;


@Log4j2
@Epic("Yandex")
@Feature("Market")
@Story("Index")
public class IndexPagePO extends BasePage {

    private Query logoLocator = new Query(By.cssSelector(".logo_part_market"), driver);
    private List<NavMenuItem> navMenuItems = new ArrayList<>();

    @Override
    @Step("open site")
    public void openSite() {
        log.trace("openSite");
        super.openSite();
        createAndGetWait().until(ExpectedConditions.visibilityOf(logoLocator.findWebElement()));
        waitPageIsLoaded();
        saveScreenShotForAllure();
    }

    @Step("read tabs")
    public IndexPagePO readTabs() {
        log.trace("readTabs");
        createAndGetWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".n-w-tab.n-w-tab_type_navigation-menu a")));
        driver.findElements(By.cssSelector(".n-w-tab.n-w-tab_type_navigation-menu a")).forEach(e -> navMenuItems.add(new NavMenuItem(e.getText(), e.getAttribute("href"))));
        log.debug("number of submenu " + navMenuItems.size());
        log.debug("IndexPagePO.readTabs:end");
        saveScreenShotForAllure();
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

    @Step("open tab by index")
    public IndexPagePO openTab(IndexTab indexTab) {
        log.trace("openTab");
        return this;
    }

    @Step("open tab")
    public IndexPagePO openTab(String caption) {
        log.trace("openTab");
        log.debug(caption);
        Optional<NavMenuItem> navMenuItemOptional = navMenuItems.stream().filter(e -> e.getCaption().equals(caption)).findFirst();
        NavMenuItem item = navMenuItemOptional.orElseThrow(() -> new RuntimeException("Menu not found " + caption));
        log.debug(item.getHref());
        driver.get(item.getHref());
        Wait wait = createAndGetWait(10);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[data-apiary-widget-name='@MarketNode/CatalogHeader']"))));
        saveScreenShotForAllure();
        return this;
    }

}