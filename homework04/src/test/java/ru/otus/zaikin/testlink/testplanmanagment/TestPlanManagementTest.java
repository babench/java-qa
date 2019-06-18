package ru.otus.zaikin.testlink.testplanmanagment;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.testlink.Frames;
import ru.otus.zaikin.testlink.entity.TestPlanManagementEntity;
import ru.otus.zaikin.testlink.indexpage.IndexPagePO;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class TestPlanManagementTest extends DriverBase {

    private WebDriver driver;
    private TestPlanManagementPO testPlanManagementPO;
    private IndexPagePO indexPagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();

        indexPagePO = new IndexPagePO();
        indexPagePO.openSiteAndLogin();
        indexPagePO.init();
        driver.switchTo().defaultContent();
        indexPagePO.switchToFrame(Frames.mainframe);
        testPlanManagementPO = new TestPlanManagementPO();
    }

    @Test
    public void shouldCreatePopulateSaveOTUS() {
        TestPlanManagementEntity entity = new TestPlanManagementEntity();
        entity.setName("OTUS_QA_ZAIKIN");
        entity.setDescription("OTUS_QA ZAIKIN");
        entity.setIsActive(true);
        entity.setIsPublic(true);
        testPlanManagementPO.open().init().create().populate(entity).save();
        verifyEntity(entity);
    }

    @Test(invocationCount = 5)
    public void shouldCreatePopulateSave() {
        TestPlanManagementEntity entity = new TestPlanManagementEntity();
        entity.setName("OTUS_" + UUID.randomUUID().toString().substring(0, 10));
        entity.setDescription(UUID.randomUUID().toString());
        entity.setIsActive(new Random().nextInt(10) % 2 == 0);
        entity.setIsPublic(new Random().nextInt(10) % 2 == 0);
        testPlanManagementPO.open().init().create().populate(entity).save();
        verifyEntity(entity);
    }

    private void verifyEntity(TestPlanManagementEntity entity) {
        By tableRow = By.xpath("//a[contains(.,'" + entity.getName() + "')]/../..");
        assertThat(driver.findElement(tableRow).findElements(By.cssSelector("td")).get(0).getText()).isEqualTo(entity.getName());
        assertThat(driver.findElement(tableRow).findElements(By.cssSelector("td")).get(1).getText()).isEqualTo(entity.getDescription());
        if (entity.getIsActive())
            assertThat(driver.findElement(tableRow).findElements(By.cssSelector(".clickable_icon input")).get(0).getAttribute("title").split(" ")[0]).isEqualTo("Active");
        else
            assertThat(driver.findElement(tableRow).findElements(By.cssSelector(".clickable_icon input")).get(0).getAttribute("title").split(" ")[0]).isEqualTo("Inactive");

        if (entity.getIsPublic())
            assertThat(driver.findElement(tableRow).findElements(By.cssSelector(".clickable_icon img")).get(0).getAttribute("title")).isEqualTo("Public");
        else
            assertThat(driver.findElement(tableRow).findElements(By.cssSelector(".clickable_icon img")).size()).isEqualTo(0);
    }

    @Test(enabled = false)
    public void shouldDeleteCreated() {
        TestPlanManagementEntity dataValue = new TestPlanManagementEntity();
        dataValue.setName("OTUS_1");
        testPlanManagementPO.open().init().delete(dataValue);
    }
}