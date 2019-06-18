package ru.otus.zaikin.testlink.executetest;

public enum Status {
    n("Not executed"),
    p("Passed"),
    f("Failed"),
    b("Blocked");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
