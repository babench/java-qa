package ru.otus.zaikin.testlink.testsuite;

import ru.otus.zaikin.testlink.entity.TestCaseEntity;
import ru.otus.zaikin.testlink.entity.TestCaseStepEntity;
import ru.otus.zaikin.testlink.entity.TestSuiteEntity;

public class TestSuiteHelper {

    static TestSuiteEntity generateData() {
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
        return suiteEntity;
    }
}
