package ru.otus.zaikin.testlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPlanManagementEntity {
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isPublic;
}
