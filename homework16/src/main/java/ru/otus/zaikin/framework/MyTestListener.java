package ru.otus.zaikin.framework;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import static ru.otus.zaikin.framework.AllureUtils.saveScreenShotForAllure;

public class MyTestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenShotForAllure();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        saveScreenShotForAllure();
    }
}