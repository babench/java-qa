package ru.otus.zaikin.gosuslugi;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/cucumber/gosuslugi/login.feature",
        glue = "ru.otus.zaikin.framework.steps")
public class CucumberTestRunnerLoginFeature extends AbstractTestNGCucumberTests {
}