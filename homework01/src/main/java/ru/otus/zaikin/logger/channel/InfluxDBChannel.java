package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import ru.otus.zaikin.AppProperties;
import ru.otus.zaikin.logger.TestStatusChannel;

@Log4j2
public class InfluxDBChannel implements TestStatusChannel {
    private final InfluxDB inflxudb;

    public InfluxDBChannel() {
        AppProperties properties = AppProperties.getInstance();
        this.inflxudb = InfluxDBFactory.connect(
                properties.getProperty("tests.status.db.inflxudb.url"),
                properties.getProperty("tests.status.db.inflxudb.username"),
                properties.getProperty("tests.status.db.inflxudb.password"));
        inflxudb.setDatabase(properties.getProperty("tests.status.db.dbname"));

    }

    @Override
    public void send(Point point) {
        log.debug("Send to InfluxDB channel");
        log.debug("message:" + point);
        inflxudb.write(point);
    }
}