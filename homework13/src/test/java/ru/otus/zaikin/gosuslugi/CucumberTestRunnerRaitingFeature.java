package ru.otus.zaikin.gosuslugi;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/test/resources/cucumber/gosuslugi/raiting.feature",
        glue = "ru.otus.zaikin.framework.steps")
public class CucumberTestRunnerRaitingFeature extends AbstractTestNGCucumberTests {
}