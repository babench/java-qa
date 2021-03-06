package ru.otus.zaikin.yandex.market.electronica;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.yandex.market.indexpage.IndexPagePO;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

public class ElectronicaPageTest extends DriverBase {

    private WebDriver driver;
    private IndexPagePO indexPagePO;
    private ElectronicaPagePO electronicaPagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        indexPagePO = new IndexPagePO();
        indexPagePO.openSite();
    }

    @Test
    public void shouldOpenElectronicTabMobilePhones() {
        indexPagePO.readTabs().openTab("Электроника");
        electronicaPagePO = new ElectronicaPagePO();
        electronicaPagePO.openSubItem("Мобильные телефоны");
        assertThat(electronicaPagePO.getHeaderText()).isEqualTo("Мобильные телефоны");
    }
}