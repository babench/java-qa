package ru.otus.zaikin.logger;

import org.junit.runner.Description;

import java.time.Instant;

public interface TestStatusChannel {

    void send(Description description, TestStatus testStatus, Instant start);
}
