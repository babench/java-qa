package ru.otus.zaikin.testlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseStepEntity {
    private int stepOrderNo;
    private String stepAction;
    private String stepExpectedResult;
    private String stepExecutionMode;

    public TestCaseStepEntity(TestCaseStepEntity entity) {
        new TestCaseStepEntity(entity.getStepOrderNo(), entity.getStepAction(), entity.getStepExecutionMode(), entity.getStepExecutionMode());
    }
}
