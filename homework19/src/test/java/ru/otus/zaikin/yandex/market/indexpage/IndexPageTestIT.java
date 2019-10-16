package ru.otus.zaikin.yandex.market.indexpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;


@Log4j2
public class IndexPageTestIT extends DriverBase {

    private IndexPagePO indexPagePO;
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        indexPagePO = new IndexPagePO();
        indexPagePO.openSite();
    }

    @Test(description = "should open")
    public void shouldOpen() {
        log.debug("Add assert");
        assertThat(indexPagePO.getLogoText()).isEqualTo("Маркет");
    }

    @Test(description = "should read navigation")
    public void shouldReadNavigation() {
        indexPagePO.readTabs();
        int navMenuSize = indexPagePO.getNavMenuSize();
        assertThat(navMenuSize).isGreaterThan(2);
        assertThat(indexPagePO.getNavMenuItem(1).getCaption()).isEqualTo("Электроника");
        assertThat(indexPagePO.getNavMenuItem(2).getCaption()).isEqualTo("Бытовая техника");
    }

    @Test(description = "should open Electronic tab")
    public void shouldOpenElectronicTab() {
        indexPagePO.readTabs().openTab("Электроника");
        assertThat(driver.findElement(By.cssSelector("div[data-apiary-widget-name='@MarketNode/CatalogHeader']")).getText()).isEqualTo("Электроника");
    }
}