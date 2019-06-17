package ru.otus.zaikin.testlink.testplanmanagment;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.testlink.Frames;
import ru.otus.zaikin.testlink.entity.TestPlanManagementEntity;
import ru.otus.zaikin.testlink.indexpage.IndexPagePO;

import java.net.MalformedURLException;

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
    public void shouldCreateAndPopulate() {
        TestPlanManagementEntity dataValue = new TestPlanManagementEntity();
        dataValue.setTestplan_name("OTUS_1");
        dataValue.setActive("Y");
        dataValue.setIs_public("Y");
        testPlanManagementPO.open().init().create().populate(dataValue);
    }

    @Test
    public void shouldCreateAndPopulateAndSave() {
        TestPlanManagementEntity dataValue = new TestPlanManagementEntity();
        dataValue.setTestplan_name("OTUS_1");
        dataValue.setActive("Y");
        dataValue.setIs_public("Y");
        testPlanManagementPO.open().init().create().populate(dataValue).save();
        /*TODO: add assertThat*/
    }

    @Test
    public void shouldDeleteCreated() {
        TestPlanManagementEntity dataValue = new TestPlanManagementEntity();
        dataValue.setTestplan_name("OTUS_1");
        testPlanManagementPO.open().init().delete(dataValue);
    }
}