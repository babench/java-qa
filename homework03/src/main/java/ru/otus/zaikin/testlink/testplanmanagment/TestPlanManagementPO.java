package ru.otus.zaikin.testlink.testplanmanagment;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.testlink.BasePage;
import ru.otus.zaikin.testlink.entity.TestPlanManagementEntity;

@Log4j2
public class TestPlanManagementPO extends BasePage {

    /*localor on index page*/
    private Query activateLocator = new Query(By.linkText("Test Plan Management"), driver);

    private Query createTestPlan = new Query(By.name("create_testplan"), driver);
    private Query doSave = new Query(By.name("do_create"), driver);

    private Query testplan_name = new Query(By.name("testplan_name"), driver);
    private Query testplanActive = new Query(By.name("active"), driver);
    private Query testplanIspublic = new Query(By.name("is_public"), driver);


    TestPlanManagementPO open() {
        log.debug("TestPlanManagementEntity.open");
        activateLocator.findWebElement().click();
        return this;
    }

    TestPlanManagementPO create() {
        log.debug("TestPlanManagementEntity.create");
        createTestPlan.findWebElement().click();
        return this;
    }

    TestPlanManagementPO init() {
        log.debug("TestPlanManagementEntity.init");
        return this;
    }

    TestPlanManagementPO save() {
        System.out.println("TestPlanManagementEntity.save");
        doSave.findWebElement().click();
        return this;
    }

    public TestPlanManagementPO populate(String dataValue) {
        System.out.println("TestPlanManagementEntity.populateTestSuiteDetails");
        return this;
    }

    TestPlanManagementPO populate(TestPlanManagementEntity entity) {
        System.out.println("TestPlanManagementEntity.populateTestSuiteDetails");
        testplan_name.findWebElement().clear();
        testplan_name.findWebElement().sendKeys(entity.getTestplan_name());
        testplanActive.findWebElement().click();
        testplanIspublic.findWebElement().click();
        return this;
    }


    void delete(TestPlanManagementEntity dataValue) {
        /*TODO*/
    }
}