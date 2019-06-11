package ru.otus.zaikin.logger;

import lombok.extern.log4j.Log4j2;
import org.influxdb.dto.Point;

@Log4j2
public class TestStatusSender {
    public void send(final Point point) {
        log.debug("send point:" + point.toString());
        new TestStatusChannelFactory().get().send(point);
    }
}
