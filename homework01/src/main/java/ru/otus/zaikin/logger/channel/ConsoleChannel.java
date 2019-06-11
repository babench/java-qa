package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.Description;
import ru.otus.zaikin.logger.TestStatus;
import ru.otus.zaikin.logger.TestStatusChannel;

import java.time.Instant;

@Log4j2
public class ConsoleChannel implements TestStatusChannel {

    @Override
    public void send(Description description, TestStatus testStatus, Instant start) {
        log.info("Send to Console channel");
        log.debug(description.toString());
        System.out.println(description.toString());
    }
}
