package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.Point;
import ru.otus.zaikin.logger.TestStatusChannel;

@Log4j2
public class ConsoleChannel implements TestStatusChannel {

    @Override
    public void send(Point point) {
        log.debug("Send to Console channel");
        log.debug(point.toString());
        System.out.println(point.toString());
    }
}
