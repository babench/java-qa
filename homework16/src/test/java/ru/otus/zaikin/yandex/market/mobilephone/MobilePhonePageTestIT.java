package ru.otus.zaikin.yandex.market.mobilephone;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.yandex.market.compare.ComparePagePO;
import ru.otus.zaikin.yandex.market.electronica.ElectronicaPagePO;
import ru.otus.zaikin.yandex.market.indexpage.IndexPagePO;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.zaikin.yandex.market.FilterEnum.PRODUCER;
import static ru.otus.zaikin.yandex.market.FilterEnum.PRODUCT_LINE;
import static ru.otus.zaikin.yandex.market.ResultSortEnum.PRICE;
import static ru.otus.zaikin.yandex.market.SortDirection.ASC;

@Log4j2
public class MobilePhonePageTestIT extends DriverBase {

    private WebDriver driver;
    private IndexPagePO indexPagePO;
    private ElectronicaPagePO electronicaPagePO;
    private MobilePhonePagePO mobilePhonePagePO;
    private ComparePagePO comparePagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        indexPagePO = new IndexPagePO();
        indexPagePO.openSite();
    }

    @Test(description = "should open")
    public void shouldOpen() {
        openMobilePhonePage();
        assertThat(mobilePhonePagePO.getHeaderText()).containsIgnoringCase("Смартфоны");
    }

    @Test(description = "should filter")
    public void shouldFilter() {
        openMobilePhonePage();
        assertThat(mobilePhonePagePO.getHeaderText()).containsIgnoringCase("Смартфоны");

        mobilePhonePagePO.setFilter(PRODUCER, "Xiaomi");
        mobilePhonePagePO.setFilter(PRODUCT_LINE, "Redmi");
        mobilePhonePagePO.setFilter(PRODUCER, "Samsung");
        mobilePhonePagePO.setFilter(PRODUCT_LINE, "Galaxy S");

        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Xiaomi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Redmi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Samsung']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Galaxy S']")).isSelected()).isTrue();
    }

    @Test(description = "should filter and sort")
    public void shouldSortFiltered() {
        openMobilePhonePage();
        assertThat(mobilePhonePagePO.getHeaderText()).containsIgnoringCase("Смартфоны");

        mobilePhonePagePO.setFilter(PRODUCER, "Xiaomi");
        mobilePhonePagePO.setFilter(PRODUCT_LINE, "Redmi");
        indexPagePO.sortBy(PRICE, ASC);
        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Xiaomi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Redmi']")).isSelected()).isTrue();
    }

    @Test(description = "should compare top products")
    public void shouldCompareTopProductsOfProducer() {
        openMobilePhonePage();
        assertThat(mobilePhonePagePO.getHeaderText()).containsIgnoringCase("Смартфоны");

        electronicaPagePO.setFilter(PRODUCER, "Xiaomi");
        electronicaPagePO.setFilter(PRODUCT_LINE, "Redmi");
        indexPagePO.sortBy(PRICE, ASC);
        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Xiaomi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Redmi']")).isSelected()).isTrue();
        comparePagePO = new ComparePagePO();

        comparePagePO.addToComparing(0);
        assertThat(indexPagePO.getPopupInformerText()).contains("добавлен к сравнению");
        indexPagePO.closeInformer();

        comparePagePO.addToComparing(1);
        assertThat(indexPagePO.getPopupInformerText()).contains("добавлен к сравнению");
        indexPagePO.closeInformer();

        comparePagePO.performComparing();

        comparePagePO.comparingShowAll();
        long count1 = driver.findElements(By.cssSelector(".n-compare-row-name")).stream().filter(p -> p.getText().contains("ОПЕРАЦИОННАЯ СИСТЕМА")).count();
        assertThat(count1).isEqualTo(1L);

        comparePagePO.comparingShowDiff();
        long count2 = driver.findElements(By.cssSelector(".n-compare-row-name")).stream().filter(p -> p.getText().contains("ОПЕРАЦИОННАЯ СИСТЕМА")).count();
        assertThat(count2).isEqualTo(0L);
    }

    @Test(description = "should compare cheapest")
    public void shouldCompareCheapestBetweenProducers() {
        openMobilePhonePage();
        assertThat(mobilePhonePagePO.getHeaderText()).containsIgnoringCase("Смартфоны");

        mobilePhonePagePO.setFilter(PRODUCER, "Xiaomi");
        mobilePhonePagePO.setFilter(PRODUCER, "Samsung");
        mobilePhonePagePO.setFilter(PRODUCT_LINE, "Redmi");
        mobilePhonePagePO.setFilter(PRODUCT_LINE, "Galaxy S");
        indexPagePO.sortBy(PRICE, ASC);

        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Xiaomi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Redmi']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Производитель Samsung']")).isSelected()).isTrue();
        assertThat(driver.findElement(By.cssSelector("input[name='Линейка Galaxy S']")).isSelected()).isTrue();
        comparePagePO = new ComparePagePO();
        comparePagePO.addToComparing("Redmi");
        assertThat(indexPagePO.getPopupInformerText()).contains("добавлен к сравнению");
        indexPagePO.closeInformer();

        comparePagePO.addToComparing("Galaxy");
        assertThat(indexPagePO.getPopupInformerText()).contains("добавлен к сравнению");
        indexPagePO.closeInformer();
        comparePagePO.performComparing();

        comparePagePO.comparingShowAll();
        assertThat(comparePagePO.isDeltaContains("ОПЕРАЦИОННАЯ СИСТЕМА")).isTrue();

        comparePagePO.comparingShowDiff();
        assertThat(comparePagePO.isDeltaContains("ОПЕРАЦИОННАЯ СИСТЕМА")).isFalse();
        log.debug("done");
    }

    private void openMobilePhonePage() {
        indexPagePO.readTabs().openTab("Электроника");
        electronicaPagePO = new ElectronicaPagePO();
        electronicaPagePO.openSubItem("Смартфоны");
        mobilePhonePagePO = new MobilePhonePagePO();
    }
}