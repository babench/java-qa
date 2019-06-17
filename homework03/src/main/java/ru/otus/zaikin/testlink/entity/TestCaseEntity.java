package ru.otus.zaikin.testlink.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestCaseEntity {

    private String testCaseTitle;
    private String testCaseSummary;
    private String testCasePreconditions;
    private List<TestCaseStepEntity> steps;

    public TestCaseEntity() {
        steps = new ArrayList<>();
    }

    public void addStep(TestCaseStepEntity stepEntity) {
        steps.add(stepEntity);
    }

    public int getStepsSize() {
        return steps.size();
    }

    public TestCaseStepEntity getStep(int index) {
        return steps.get(index);
    }
}
