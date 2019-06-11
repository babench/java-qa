package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.Point;
import ru.otus.zaikin.logger.TestResultChannel;

@Log4j2
public class ConsoleChannel implements TestResultChannel {

    @Override
    public void send(Point point) {
        log.debug("Console channel send ");
        log.debug(point.toString());
    }
}
