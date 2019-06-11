package ru.otus.zaikin.logger;

import lombok.extern.log4j.Log4j2;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.time.Instant;

@Log4j2
public class CustomTestWatcher extends TestWatcher {
    private Instant start;

    @Override
    public Statement apply(Statement base, Description description) {
        return super.apply(base, description);
    }

    @Override
    protected void starting(Description description) {
        super.starting(description);
        start = Instant.now();
    }

    @Override
    protected void succeeded(Description description) {
        super.succeeded(description);
        sendTestMethodStatus(description, TestStatus.PASS);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        super.failed(e, description);
        sendTestMethodStatus(description, TestStatus.FAIL);
    }

    @Override
    protected void skipped(AssumptionViolatedException e, Description description) {
        super.skipped(e, description);
        sendTestMethodStatus(description, TestStatus.SKIPPED);
    }

    private void sendTestMethodStatus(Description description, TestStatus testStatus) {
        new TestStatusSender().send(description, testStatus, start);
    }
}