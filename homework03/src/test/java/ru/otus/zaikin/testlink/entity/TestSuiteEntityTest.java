package ru.otus.zaikin.testlink.entity;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class TestSuiteEntityTest {

    @Test
    public void should2Json() {
        TestSuiteEntity suiteEntity = new TestSuiteEntity();
        suiteEntity.setSuiteName("TEST_SUITE_001");
        suiteEntity.setSuiteDetails("Details of Test suite 001");

        {
            TestCaseEntity testCaseEntity = new TestCaseEntity();
            testCaseEntity.setTestCaseTitle("TC_001");
            testCaseEntity.setTestCaseSummary("Summary of TC 001");
            testCaseEntity.setTestCasePreconditions("Precondition");
            testCaseEntity.addStep(new TestCaseStepEntity(1, "step 001_1", "step 001_1 completed", "M"));
            testCaseEntity.addStep(new TestCaseStepEntity(2, "step 001_2", "step 001_2 completed", "M"));
            testCaseEntity.addStep(new TestCaseStepEntity(3, "step 001_3", "step 001_3 completed", "M"));
            suiteEntity.addTestCase(testCaseEntity);
        }

        {
            TestCaseEntity testCaseEntity = new TestCaseEntity();
            testCaseEntity.setTestCaseTitle("TC_002");
            testCaseEntity.setTestCaseSummary("Summary of TC 002");
            testCaseEntity.setTestCasePreconditions("Precondition");
            testCaseEntity.addStep(new TestCaseStepEntity(1, "step 002_1", "step 002_1 completed", "M"));
            testCaseEntity.addStep(new TestCaseStepEntity(2, "step 002_2", "step 002_2 completed", "M"));
            testCaseEntity.addStep(new TestCaseStepEntity(3, "step 002_3", "step 002_3 completed", "M"));
            suiteEntity.addTestCase(testCaseEntity);
        }

        Gson gson = new Gson();
        String json = gson.toJson(suiteEntity);
        assertThat(json.length()).isGreaterThan(10);
    }

    @Test
    public void shouldReadJson() {
        String json = "{\"suiteName\":\"TEST_SUITE_001\",\"suiteDetails\":\"Details of Test suite 001\",\"testcases\":[{\"testCaseTitle\":\"TC_001\",\"testCaseSummary\":\"Summary of TC 001\",\"testCasePreconditions\":\"Precondition\",\"steps\":[{\"stepOrderNo\":1,\"stepAction\":\"step 001_1\",\"stepExpectedResult\":\"step 001_1 completed\",\"stepExecutionMode\":\"M\"},{\"stepOrderNo\":2,\"stepAction\":\"step 001_2\",\"stepExpectedResult\":\"step 001_2 completed\",\"stepExecutionMode\":\"M\"},{\"stepOrderNo\":3,\"stepAction\":\"step 001_3\",\"stepExpectedResult\":\"step 001_3 completed\",\"stepExecutionMode\":\"M\"}]},{\"testCaseTitle\":\"TC_002\",\"testCaseSummary\":\"Summary of TC 002\",\"testCasePreconditions\":\"Precondition\",\"steps\":[{\"stepOrderNo\":1,\"stepAction\":\"step 002_1\",\"stepExpectedResult\":\"step 002_1 completed\",\"stepExecutionMode\":\"M\"},{\"stepOrderNo\":2,\"stepAction\":\"step 002_2\",\"stepExpectedResult\":\"step 002_2 completed\",\"stepExecutionMode\":\"M\"},{\"stepOrderNo\":3,\"stepAction\":\"step 002_3\",\"stepExpectedResult\":\"step 002_3 completed\",\"stepExecutionMode\":\"M\"}]}]}";
        Gson gson = new Gson();
        TestSuiteEntity suiteEntity = gson.fromJson(json, TestSuiteEntity.class);
        assertThat(suiteEntity.getTestcases()).hasSize(2);
        log.debug(suiteEntity);
    }
}