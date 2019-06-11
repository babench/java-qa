package ru.otus.zaikin;

import org.influxdb.dto.Point;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import ru.otus.zaikin.logger.TestStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

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
        Point point = Point.measurement("testmethod")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("testclass", description.getClassName())
                .tag("methodname", description.getMethodName())
                .tag("result", testStatus.toString())
                .addField("duration", Duration.between(start, Instant.now()).toMillis())
                .build();
        ResultSender.send(point);
    }
}
