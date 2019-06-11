package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.Point;
import ru.otus.zaikin.logger.TestResultChannel;

@Log4j2
public class SlackChannel implements TestResultChannel {

    @Override
    public void send(Point point) {
       log.debug("Slack channel send "+point);
    }
}
