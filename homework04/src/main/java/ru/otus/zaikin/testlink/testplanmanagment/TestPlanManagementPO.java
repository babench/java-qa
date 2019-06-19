package ru.otus.zaikin.testlink.testplanmanagment;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.testlink.BasePage;
import ru.otus.zaikin.testlink.entity.TestPlanManagementEntity;

@Log4j2
public class TestPlanManagementPO extends BasePage {

    /*localor on index page*/
    private Query activateLocator = new Query(By.linkText("Test Plan Management"), driver);

    private Query createTestPlan = new Query(By.name("create_testplan"), driver);
    private Query doSave = new Query(By.name("do_create"), driver);

    /*maintenance form*/
    private Query nameLocator = new Query(By.name("testplan_name"), driver);
    private final int descriptionLocatorPosition = 1;
    private Query activeLocator = new Query(By.name("active"), driver);
    private Query publicLocator = new Query(By.name("is_public"), driver);


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
        log.debug("TestPlanManagementEntity.save");
        doSave.findWebElement().click();
        return this;
    }

    public TestPlanManagementPO populate(String dataValue) {
        log.debug("TestPlanManagementEntity.populateTestSuiteDetails");
        return this;
    }

    TestPlanManagementPO populate(TestPlanManagementEntity entity) {
        log.debug("TestPlanManagementEntity.populateTestSuiteDetails");

        nameLocator.findWebElement().clear();
        nameLocator.findWebElement().sendKeys(entity.getName());

        if (entity.getDescription() != null && !entity.getDescription().isEmpty()) {
            sendKeysToContents(descriptionLocatorPosition, entity.getDescription());
            switchToMainFrame();
        }

        {
            if (entity.getIsActive() && !activeLocator.findWebElement().isSelected())
                activeLocator.findWebElement().click();

            if (!entity.getIsActive() && activeLocator.findWebElement().isSelected())
                activeLocator.findWebElement().click();
        }

        {
            if (entity.getIsPublic() && !publicLocator.findWebElement().isSelected())
                publicLocator.findWebElement().click();

            if (!entity.getIsPublic() && publicLocator.findWebElement().isSelected())
                publicLocator.findWebElement().click();
        }

        return this;
    }

    void delete(TestPlanManagementEntity dataValue) {
        /*TODO*/
    }
}