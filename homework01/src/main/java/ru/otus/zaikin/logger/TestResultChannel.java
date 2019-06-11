package ru.otus.zaikin.logger;

import org.influxdb.dto.Point;

public interface TestResultChannel {

    void send(final Point point);
}
