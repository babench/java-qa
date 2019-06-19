package ru.otus.zaikin.testlink.testsuite;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.testlink.entity.TestCaseStepEntity;
import ru.otus.zaikin.testlink.entity.TestSuiteEntity;
import ru.otus.zaikin.testlink.indexpage.IndexPagePO;

import java.net.MalformedURLException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class TestSuiteTest extends DriverBase {

    private WebDriver driver;
    private TestSuitePO testSuitePO;
    private IndexPagePO indexPagePO;


    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        indexPagePO = new IndexPagePO();
        indexPagePO.openSiteAndLogin();
        indexPagePO.init();
        indexPagePO.switchToMainFrame();
        testSuitePO = new TestSuitePO();
    }

    @Test
    public void shouldSaveTestSuite() {
        TestSuiteEntity entity = TestSuiteHelper.generateData();
        entity.setSuiteName("TEST_SUITE_001");
        createTestSuite(entity);
        assertThat(testSuitePO.readFeedBack()).isEqualTo(TestSuiteMsg.CREATED.toString());
    }

    @Test
    public void shouldAddTestCases() {
        TestSuiteEntity entity = TestSuiteHelper.generateData();
        entity.setSuiteName("TEST_SUITE_002");
        createTestSuite(entity);
        fillTestCases(entity);
    }

    @Test(invocationCount = 10)
    public void shouldAddSteps() {
        TestSuiteEntity entity = TestSuiteHelper.generateData();
        entity.setSuiteName("TEST_SUITE_" + new Random().nextInt(1000));
        entity.getTestcases().get(0).addStep(new TestCaseStepEntity(3, "004_4 create step", "Created", "M"));
        createTestSuite(entity);
        fillTestCases(entity);
        fillTestCasesSteps(entity);
    }

    private void createTestSuite(TestSuiteEntity entity) {
        testSuitePO.open().activateWorkFrame().showActions().create().populateTestSuiteDetails(entity).save();
    }

    private void fillTestCases(TestSuiteEntity entity) {
        testSuitePO.populateTestCases(entity);
    }

    private void fillTestCasesSteps(TestSuiteEntity entity) {
        testSuitePO.populateTestCasesSteps(entity);
    }
}