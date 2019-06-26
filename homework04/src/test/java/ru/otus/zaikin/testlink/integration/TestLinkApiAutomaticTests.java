package ru.otus.zaikin.testlink.integration;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionType;
import br.eti.kinoshita.testlinkjavaapi.constants.TestCaseDetails;
import br.eti.kinoshita.testlinkjavaapi.model.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class TestLinkApiAutomaticTests {

    TestLinkAPI client;
    String url = "http://192.168.99.100/lib/api/xmlrpc/v1/xmlrpc.php";
    String devKey = "da552ac72dcc80b991d257cc13224b33";


    @BeforeMethod
    public void setUp() {
        client = new TestLinkApiHolder(url, devKey).getClient();
    }

    @Test
    public void shouldBe() {
        assertThat(client).isNotNull();
        log.debug(client.about());
    }

    @Test
    public void shouldReadProjects() {
        TestProject[] projects = client.getProjects();
        Arrays.asList(projects).forEach(log::debug);
        assertThat(projects.length).isGreaterThan(0);
    }

    @Test
    public void shouldGetTestSuites() {
        TestSuite[] testSuitesForTestPlan = client.getTestSuitesForTestPlan(7);
        Arrays.asList(testSuitesForTestPlan).forEach(log::debug);
    }

    @Test
    public void shouldReadTestPlans() {
        TestPlan[] projectTestPlans = client.getProjectTestPlans(1);
        Arrays.asList(projectTestPlans).forEach(log::info);
    }

    @Test
    public void shouldReadTestSuitesOfProject() {
        TestSuite[] firstLevelTestSuitesForTestProject = client.getFirstLevelTestSuitesForTestProject(1);
        Arrays.asList(firstLevelTestSuitesForTestProject).forEach(log::debug);
    }

    @Test
    public void shouldReadBuild() {
        Arrays.asList(client.getBuildsForTestPlan(7)).forEach(log::debug);
    }

    @Test
    public void shouldReadCases() {
        TestCase testCase = client.getTestCase(465, null, 1);
        log.debug(testCase);
    }

    @Test
    public void shouldReadCase1() {
        TestCase[] testCasesForTestSuite = client.getTestCasesForTestSuite(464, true, TestCaseDetails.SIMPLE);
        Arrays.asList(testCasesForTestSuite).forEach(log::debug);
        testCasesForTestSuite[0].getExternalId();
    }

    @Test
    public void shouldReadTestCases() {
        Integer testCaseIDByName = client.getTestCaseIDByName("TC_001", "TEST_SUITE_662", "OTUS_QA_ZAIKIN",
                "JAQA-86 :: Version: 1 :: TC_001");
        log.debug(testCaseIDByName);
    }

    @Test(description = "should process Test case with Step details and screenshot")
    public void shouldSaveExecutionResults() {
        List<TestCaseStepResult> steps = new ArrayList<>();
        steps.add(new TestCaseStepResult(1, ExecutionStatus.PASSED, "Step notes", true, ExecutionType.AUTOMATED));
        ReportTCResultResponse response;
        response = client.reportTCResult(465, null, 7, ExecutionStatus.PASSED, steps, 1,
                "BUILD_001", "Test case completed. Input relevant information", 10, true, null, null, null, null, true, null, "2019-06-26 23:24:25");
        assertThat(response).isNotNull();
        assertThat(response.getExecutionId()).isGreaterThan(0);

        //attach extra
        File attachmentFile = new File("c:\\if_chrome_245982.png");

        String fileContent = null;

        try {
            byte[] byteArray = FileUtils.readFileToByteArray(attachmentFile);
            fileContent = new String(Base64.encodeBase64(byteArray));
        } catch (IOException e) {
            e.printStackTrace(System.err);
            System.exit(-1);
        }

        Attachment attachment = client.uploadExecutionAttachment(
                response.getExecutionId(), //executionId
                "Setting customer plan", //title
                "In this screen the attendant is defining the customer plan", //description
                "screenshot_customer_plan_" + System.currentTimeMillis() + ".jpg", //fileName
                "image/png", //fileType
                fileContent); //content

    }
}
