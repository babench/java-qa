package ru.otus.zaikin.logger;

public enum TestStatus {
    ALL(0),
    PASS(1),
    SKIPPED(2),
    FAIL(3),
    OFF(100);

    private final int severity;

    TestStatus(int severity) {
        this.severity = severity;
    }
}
