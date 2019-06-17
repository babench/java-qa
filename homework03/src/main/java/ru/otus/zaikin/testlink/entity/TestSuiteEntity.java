package ru.otus.zaikin.testlink.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestSuiteEntity {
    private String suiteName;
    private String suiteDetails;
    private List<TestCaseEntity> testcases;

    public TestSuiteEntity(String suiteName, String suiteDetails) {
        this();
        this.suiteName = suiteName;
        this.suiteDetails = suiteDetails;
    }

    public TestSuiteEntity() {
        testcases = new ArrayList<>();
    }

    public void addTestCase(TestCaseEntity caseEntity) {
        testcases.add(caseEntity);
    }

    public int getTestCasesSize() {
        return testcases.size();
    }

    public TestCaseEntity getTestCase(int index) {
        return testcases.get(index);
    }
}
