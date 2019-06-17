package ru.otus.zaikin.testlink.testsuite;

public enum TestSuiteMsg {
    CREATED("Test Suite created");

    private String value;

    TestSuiteMsg(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }
}
