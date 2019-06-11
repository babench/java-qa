package ru.otus.zaikin.logger;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Description;
import ru.otus.zaikin.AppProperties;

import java.time.Instant;

@Log4j2
public class TestStatusSender {
    public void send(Description description, TestStatus testStatus, Instant start) {
        String severity = AppProperties.getInstance().getProperty("tests.status.severity");
        log.info("system severity for logging is " + severity);
        if (severity != null) {
            TestStatus systemTestStatus = TestStatus.valueOf(severity);
            if (systemTestStatus.ordinal() <= testStatus.ordinal()) {
                new TestStatusChannelFactory().get().send(description, testStatus, start);
            }
        }
    }
}
