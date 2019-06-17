package ru.otus.zaikin.testlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanManagementEntity {
    private String testplan_name;
    private String active;
    private String is_public;
}
