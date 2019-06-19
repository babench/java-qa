package ru.otus.zaikin.testlink.executetest;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.otus.zaikin.testlink.BasePage;
import ru.otus.zaikin.testlink.Frames;

@Log4j2
public class ExecuteTestPO extends BasePage {

    /*localor on index page*/
    private Query activateLocator = new Query(By.linkText("Execute Tests"), driver);

    /*Initial stat*/
    private Query exectionHistoryDivLocalor = new Query(By.id("exec_history_title"), driver);

    /*Steps*/
    private Query stepsExecutionLocator = new Query(By.cssSelector(".simple"));

    /*Final Execution Status*/
    private Query executionDurationLocator = new Query(By.id("execution_duration"), driver);

    private final String finalStatusCurrent = "fastExec";
    private final String finalStatusNext = "fastExecNext";

    //dynamic
    //private Query finalStatusNext = new Query(By.id("fastExecNext"), driver);

    private Query executionHistoryTable = new Query(By.cssSelector(".exec_history"));

    ExecuteTestPO open() {
        activateLocator.findWebElement().click();
        return this;
    }

    ExecuteTestPO activateAnyNotProcessedTest() {
        log.debug("ExecuteTestPO.activateAnyNotProcessedTest");
        switchToFrame(Frames.treeframe);
        driver.findElement(By.id("expand_tree")).click();

        /*try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        Wait explicitWait = new WebDriverWait(driver, 10);
        explicitWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".x-tree-node-leaf span.light_not_run"), 0));
        log.debug("numbers:" + driver.findElements(By.cssSelector(".x-tree-node-leaf span.light_not_run")).size());
        explicitWait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.cssSelector(".x-tree-node-leaf span.light_not_run")).get(0)));

        driver.findElements(By.cssSelector(".x-tree-node-leaf span.light_not_run")).get(0).click();
        switchToMainFrame();
        switchToFrame(Frames.workframe);
        return this;
    }

    public String getCurrentTestCaseName() {
        log.debug("ExecuteTestPO.getCurrentTestCaseName");
        return driver.findElement(By.cssSelector(".exec_tc_title")).getText().split("::")[0].replace("Test Case ", "").trim();
    }

    public int getExecutionId() {
        log.debug("ExecuteTestPO.getExecutionId");
        /*/*final table class="invisible"
         * table \ tr\td \ textarea from id notes[104] parse id and use later*/
        return Integer.valueOf(driver.findElement(By.id("execSetResults")).findElement(By.cssSelector(".invisible")).findElement(By.cssSelector("textarea")).getAttribute("name").replace("notes[", "").replace("]", ""));
    }

    public int getNumberOfSteps() {
        return driver.findElements(By.cssSelector(".simple tr[id^='step_row']")).size();
    }

    public WebElement getStepElement(int index) {
        return driver.findElements(By.cssSelector(".simple tr[id^='step_row']")).get(index);
    }

    public ExecuteTestPO processStep(int index, String executionNotes, Status stepStatus) {
        log.debug("ExecuteTestPO.processStep");
        WebElement row = driver.findElements(By.cssSelector(".simple tr[id^='step_row']")).get(index);
        row.findElements(By.cssSelector("td")).get(4).findElement(By.cssSelector("textarea")).sendKeys(executionNotes);
        new Select(row.findElements(By.cssSelector("td")).get(5).findElement(By.cssSelector("select"))).selectByValue(stepStatus.name());
        return this;
    }

    public ExecuteTestPO setFinalNote(String note) {
        log.debug("ExecuteTestPO.setFinalNote");
        driver.findElement(By.id("execSetResults")).findElement(By.cssSelector(".invisible")).findElement(By.cssSelector("textarea")).sendKeys(note);
        return this;
    }

    public ExecuteTestPO setExecutionDuration(int duration) {
        log.debug("ExecuteTestPO.setExecutionDuration");
        executionDurationLocator.findWebElement().sendKeys(String.valueOf(duration));
        return this;
    }

    public ExecuteTestPO submitExecutionStatus(Status status, ResultMode resultMode) {
        log.debug("ExecuteTestPO.submitExecutionStatus");
        Query submitButton = new Query(By.id(resultMode.getValueOnForm() + status.name() + "_" + getExecutionId()), driver);
        submitButton.findWebElement().click();
        return this;
    }

    public WebElement getExecutionResultRow(int index) {
        log.debug("ExecuteTestPO.getExecutionResultRow");
        return driver.findElements(By.cssSelector(".exec_history tr")).get(index + 1);
    }

    public WebElement getExecutionResults(int index) {
        log.debug("ExecuteTestPO.getExecutionResults");
        return driver.findElements(By.cssSelector(".exec_history tr")).get(index + 1).findElements(By.cssSelector("td")).get(2);
    }

    public String getTreeStatusOfExecution() {
        log.debug("ExecuteTestPO.getTreeStatusOfExecution");
        String currentTestCaseName = getCurrentTestCaseName();
        switchToMainFrame();
        switchToFrame(Frames.treeframe);
        String status = driver.findElement(By.partialLinkText(currentTestCaseName + ":")).findElement(By.cssSelector("span")).findElements(By.cssSelector("*")).get(0).getAttribute("class");
        switchToMainFrame();
        switchToFrame(Frames.workframe);
        return status;
    }

}
