package ru.otus.zaikin.testlink.executetest;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.testlink.Frames;
import ru.otus.zaikin.testlink.indexpage.IndexPagePO;

import java.net.MalformedURLException;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class ExecuteTestTest extends DriverBase {

    private WebDriver driver;
    private ExecuteTestPO executeTestPO;
    private IndexPagePO indexPagePO;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();

        indexPagePO = new IndexPagePO();
        indexPagePO.openSiteAndLogin();
        indexPagePO.init();
        driver.switchTo().defaultContent();
        indexPagePO.switchToFrame(Frames.mainframe);
        executeTestPO = new ExecuteTestPO();
    }

    @Test
    public void shouldOpen() {
        log.debug("ExecuteTestTest.shouldOpen");
        executeTestPO.open();
    }

    @Test
    public void shouldActivateTree() {
        log.debug("ExecuteTestTest.shouldActivateTree");
        executeTestPO.open().activateAnyNotProcessedTest();
        log.debug("ExecuteTestTest.shouldActivateTree");
        /*todo:move to PO*/
        assertThat(driver.findElement(By.cssSelector(".exec_history_title")).isEnabled()).isTrue();
        assertThat(driver.findElements(By.cssSelector(".not_run")).size()).isEqualTo(1);
        assertThat(driver.findElement(By.cssSelector(".not_run")).getCssValue("background-color")).isEqualTo("rgba(0, 0, 0, 1)");
    }

    @Test
    public void shouldGetExecutionId() {
        int executionId = executeTestPO.open().activateAnyNotProcessedTest().getExecutionId();
        assertThat(executionId).isGreaterThan(1);
    }

    @Test
    public void shouldGetCaseName() {
        String caseName = executeTestPO.open().activateAnyNotProcessedTest().getCurrentTestCaseName();
        assertThat(caseName).isNotEmpty();
        assertThat(caseName).startsWith("JAQA");
    }

    @Test
    public void shouldGetNumberOfSteps() {
        int numberOfSteps = executeTestPO.open().activateAnyNotProcessedTest().getNumberOfSteps();
        assertThat(numberOfSteps).isGreaterThan(1);
    }

    @Test
    public void shouldProcessStep1() {
        log.debug("ExecuteTestTest.shouldProcessStep1");
        executeTestPO.open().activateAnyNotProcessedTest();
        if (executeTestPO.getNumberOfSteps() > 0)
            executeTestPO.processStep(0, "Done", Status.p);
    }

    @Test
    public void shouldProcessAllStep() {
        log.debug("ExecuteTestTest.shouldProcessStep1");
        executeTestPO.open().activateAnyNotProcessedTest();
        IntStream.range(0, executeTestPO.getNumberOfSteps()).forEach(i -> executeTestPO.processStep(i, "Done", Status.p));
        log.debug("All should be processed");
        WebElement stepElement = executeTestPO.getStepElement(0);
        log.debug("assert here");
    }

    @Test
    public void shouldSetFinalNote() {
        log.debug("ExecuteTestTest.shouldSetFinalNote");
        executeTestPO.open().activateAnyNotProcessedTest();
        IntStream.range(0, executeTestPO.getNumberOfSteps()).forEach(i -> executeTestPO.processStep(i, "Done", Status.p));
        executeTestPO.setFinalNote("Done final");
        log.debug("ExecuteTestTest.shouldSetFinalNote");
    }

    @Test
    public void shouldSetDuration() {
        log.debug("ExecuteTestTest.shouldSetDuration");
        executeTestPO.open().activateAnyNotProcessedTest();
        IntStream.range(0, executeTestPO.getNumberOfSteps()).forEach(i -> executeTestPO.processStep(i, "Done", Status.p));
        executeTestPO.setExecutionDuration(3);
        log.debug("ExecuteTestTest.shouldSetDuration");
    }

    @Test
    public void shouldProcessPositiveTestCase() {
        log.debug("ExecuteTestTest.shouldProcessFinalStatus");
        executeTestPO.open().activateAnyNotProcessedTest();
        IntStream.range(0, executeTestPO.getNumberOfSteps()).forEach(i -> executeTestPO.processStep(i, "Done", Status.p));
        executeTestPO.setFinalNote("Test case completed!").setExecutionDuration(3).submitExecutionStatus(Status.p, ResultMode.Current);
        assertThat(executeTestPO.getExecutionResults(0).getText()).isEqualTo("Passed");
        assertThat(executeTestPO.getExecutionResults(0).getCssValue("background-color")).isEqualTo("rgba(0, 100, 0, 1)");
        assertThat(executeTestPO.getExecutionResults(0).getAttribute("class")).isEqualTo("passed");
        assertThat(executeTestPO.getTreeStatusOfExecution()).isEqualTo("light_passed");
        log.debug("ExecuteTestTest.shouldProcessFinalStatus");
    }

    @Test
    public void shouldProcessNegativeTestCase() {
        log.debug("ExecuteTestTest.shouldProcessFinalStatus");
        executeTestPO.open().activateAnyNotProcessedTest();
        IntStream.range(0, executeTestPO.getNumberOfSteps()).forEach(i -> executeTestPO.processStep(i, "Failed", Status.f));
        executeTestPO.setFinalNote("Test case failed!").setExecutionDuration(3).submitExecutionStatus(Status.f, ResultMode.Current);
        assertThat(executeTestPO.getExecutionResults(0).getText()).isEqualTo("Failed");
        assertThat(executeTestPO.getExecutionResults(0).getAttribute("class")).isEqualTo("failed");
        assertThat(executeTestPO.getTreeStatusOfExecution()).isEqualTo("light_failed");
        log.debug("ExecuteTestTest.shouldProcessFinalStatus");
    }
}