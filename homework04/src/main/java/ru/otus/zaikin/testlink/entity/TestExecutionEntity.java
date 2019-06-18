package ru.otus.zaikin.testlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.zaikin.testlink.executetest.ResultMode;
import ru.otus.zaikin.testlink.executetest.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestExecutionEntity {
    private int id;
    private int executionDuration;
    private ResultMode resultMode;
    private Status eachStepStatus;
    private Status executionStatus;
}
