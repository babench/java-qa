package ru.otus.zaikin.testlink.testsuite;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.testlink.BasePage;
import ru.otus.zaikin.testlink.Frames;
import ru.otus.zaikin.testlink.entity.TestCaseEntity;
import ru.otus.zaikin.testlink.entity.TestCaseStepEntity;
import ru.otus.zaikin.testlink.entity.TestSuiteEntity;

import java.util.List;

@Log4j2
public class TestSuitePO extends BasePage {

    /*localor on index page*/
    private Query activateLocator = new Query(By.linkText("Test Specification"), driver);

    private Query actions = new Query(By.cssSelector("img[title='Actions']"), driver);
    private Query toolbalNew = new Query(By.id("new_testsuite"), driver);
    /*Test Suite new*/
    private Query testsuiteSaveButton = new Query(By.name("add_testsuite_button"), driver);
    private Query testsuiteCancelButton = new Query(By.name("go_back"), driver);
    /*Test suite details*/
    private Query testsuiteName = new Query(By.id("name"), driver);

    private Query expandTree = new Query(By.id("expand_tree"), driver);

    /*Test Case*/
    /*TODO: refactoring: move to Test Case sub page*/
    private Query testCaseNewButton = new Query(By.id("create_tc"), driver);
    private Query testCaseDoCreate = new Query(By.id("do_create_button"), driver);
    private Query testcaseName = new Query(By.id("testcase_name"), driver);

    /*Test Steps*/
    /*TODO: refactoring: move to Test Case Step sub page*/
    private Query stepCreate = new Query(By.name("create_step"), driver);
    private Query stepNew = new Query(By.id("new_step"), driver);
    private Query doUpdateStepAndExit = new Query(By.id("do_update_step_and_exit"), driver);


    TestSuitePO open() {
        activateLocator.findWebElement().click();
        return this;
    }

    TestSuitePO activateWorkFrame() {
        switchToFrame(Frames.workframe);
        return this;
    }

    TestSuitePO showActions() {
        new WebDriverWait(driver, 2).until(d -> ExpectedConditions.presenceOfElementLocated(actions.locator()));
        new WebDriverWait(driver, 2).until(d -> ExpectedConditions.elementToBeClickable(actions.locator()));
        actions.findWebElement().click();
        return this;
    }

    TestSuitePO create() {
        new WebDriverWait(driver, 2).until(d -> ExpectedConditions.presenceOfElementLocated(toolbalNew.locator()));
        new WebDriverWait(driver, 2).until(d -> ExpectedConditions.elementToBeClickable(toolbalNew.locator()));
        toolbalNew.findWebElement().click();
        return this;
    }

    TestSuitePO populateTestSuiteDetails(TestSuiteEntity entity) {
        testsuiteName.findWebElement().clear();
        testsuiteName.findWebElement().sendKeys(entity.getSuiteName());
        populateDetails(entity.getSuiteDetails());
        return this;
    }

    private void populateDetails(String details) {

        if (details != null && !details.isEmpty()) {
            sendKeysToContents(1, details);
            backToFrame(Frames.workframe);
        }
    }

    TestSuitePO save() {
        testsuiteSaveButton.findWebElement().click();
        return this;
    }

    private TestSuitePO chooseSuite(String suiteName) {
        WebElement suiteElement = getSuiteElement(suiteName);
        new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(getSuiteElement(suiteName)));
        suiteElement.click();
        log.debug("element clicked");
        return this;
    }

    private WebElement getSuiteElement(String suiteName) {

        Wait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".x-tree-node>div:not(.x-tree-node-leaf)>a")));

        List<WebElement> elements = driver.findElements(By.cssSelector(".x-tree-node>div:not(.x-tree-node-leaf)>a"));
        for (WebElement element : elements) {
            String elementText = element.getAttribute("text");
            if (elementText.contains(suiteName + " (")) {
                log.debug("matched with " + elementText);
                return element;
            }
        }
        throw new RuntimeException("Element not found");
    }

    private TestSuitePO activateTreeFrame() {
        switchToFrame(Frames.treeframe);
        expandTree.findWebElement().click();
        return this;
    }

    private TestSuitePO addTestCase(TestCaseEntity testCaseEntity) {
        Wait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.presenceOfElementLocated(testCaseNewButton.locator()));
        wait.until(ExpectedConditions.elementToBeClickable(testCaseNewButton.locator()));
        testCaseNewButton.findWebElement().click();
        /*TODO: testcase sub page candidate*/

        testcaseName.findWebElement().sendKeys(testCaseEntity.getTestCaseTitle());
        log.debug("populate summary");
        if (testCaseEntity.getTestCaseSummary() != null && !testCaseEntity.getTestCaseSummary().isEmpty()) {
            sendKeysToContents(1, testCaseEntity.getTestCaseSummary());
            backToFrame(Frames.workframe);
        }

        log.debug("populate preconditions");
        if (testCaseEntity.getTestCasePreconditions() != null && !testCaseEntity.getTestCasePreconditions().isEmpty()) {
            sendKeysToContents(2, testCaseEntity.getTestCasePreconditions());
            backToFrame(Frames.workframe);
        }

        log.debug("done");
        return this;
    }

    private TestSuitePO saveTestCase() {
        testCaseDoCreate.findWebElement().click();
        return this;
    }

    private TestSuitePO addStep(TestCaseStepEntity testCaseStepEntity) {
        switchToMainFrame();
        switchToFrame(Frames.workframe);
        Wait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(stepCreate.locator()));
        stepCreate.findWebElement().click();

        WebElement newCell = stepNew.findWebElement().findElements(By.cssSelector("td")).get(1);
        newCell.click();

        if (testCaseStepEntity.getStepAction() != null && !testCaseStepEntity.getStepAction().isEmpty()) {
            sendKeysToContents(1, testCaseStepEntity.getStepAction());
            backToFrame(Frames.workframe);
        }

        if (testCaseStepEntity.getStepExpectedResult() != null && !testCaseStepEntity.getStepExpectedResult().isEmpty()) {
            sendKeysToContents(2, testCaseStepEntity.getStepExpectedResult());
            backToFrame(Frames.workframe);
        }
        doUpdateStepAndExit.findWebElement().click();
        return this;
    }


    TestSuitePO populateTestCases(TestSuiteEntity entity) {
        log.debug("TestSuitePO.populateTestCases");
        for (int i = 0; i < entity.getTestCasesSize(); i++) {
            TestCaseEntity entityTestCase = entity.getTestCase(i);
            String title = "null";
            while (!title.equals("Test Suite : " + entity.getSuiteName())) {
                title = activateSuiteAndGetTitle(entity);
            }

            showActions().addTestCase(entityTestCase).saveTestCase();

            switchToMainFrame();
            activateTreeFrame().chooseSuite(entity.getSuiteName());
        }
        return this;
    }

    private String activateSuiteAndGetTitle(TestSuiteEntity entity) {
        /*activate suite in tree*/
        switchToMainFrame();
        activateTreeFrame().chooseSuite(entity.getSuiteName());

        /*go to main and input in workframe*/
        switchToMainFrame();
        activateWorkFrame();
        String title = "null";
        if (driver.findElements(By.cssSelector(".simple>tbody>tr>th")).size() > 0) {
            title = driver.findElement(By.cssSelector(".simple>tbody>tr>th")).getText();
        }
        return title;
    }

    TestSuitePO populateTestCasesSteps(TestSuiteEntity entity) {
        log.debug("TestSuitePO.populateTestCasesSteps");
        for (int i = 0; i < entity.getTestCasesSize(); i++) {
            /*choose suite*/
            switchToMainFrame();
            activateTreeFrame().chooseSuite(entity.getSuiteName());
            TestCaseEntity entityTestCase = entity.getTestCase(i);
            WebElement suiteElement = getSuiteElement(entity.getSuiteName());
            Integer suiteId = Integer.valueOf(suiteElement.getAttribute("href").replace("javascript:ETS(", "").replace(")", ""));

            /*choose test case in tree*/
            List<WebElement> elements = driver.findElements(By.cssSelector(".x-tree-node-leaf a"));
            for (WebElement element : elements) {
                if (element.getAttribute("text").split(":")[1].equals(entityTestCase.getTestCaseTitle())) {
                    Integer elementId = Integer.valueOf(element.getAttribute("href").replace("javascript:ET(", "").replace(")", ""));
                    if (elementId > suiteId) {
                        log.debug("matched with " + element);
                        element.click();
                        switchToMainFrame();
                        activateWorkFrame().showActions();
                        break;
                    }
                }
            }

            /*we are on main frame and ready input steps*/

            for (int j = 0; j < entityTestCase.getStepsSize(); j++) {
                addStep(entityTestCase.getStep(j));
            }
        }
        return this;
    }
}