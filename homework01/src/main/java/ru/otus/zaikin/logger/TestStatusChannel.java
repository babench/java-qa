package ru.otus.zaikin.logger;

import org.influxdb.dto.Point;

public interface TestStatusChannel {

    void send(final Point point);
}
