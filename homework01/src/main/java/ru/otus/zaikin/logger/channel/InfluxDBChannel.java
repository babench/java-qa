package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.junit.runner.Description;
import ru.otus.zaikin.AppProperties;
import ru.otus.zaikin.logger.TestStatus;
import ru.otus.zaikin.logger.TestStatusChannel;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

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
    public void send(Description description, TestStatus testStatus, Instant start) {
        log.debug("Send to InfluxDB channel");
        Point point = Point.measurement("testmethod")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("testclass", description.getClassName())
                .tag("methodname", description.getMethodName())
                .tag("result", testStatus.toString())
                .addField("duration", Duration.between(start, Instant.now()).toMillis())
                .build();
        log.debug("message:" + point);
        inflxudb.write(point);
    }
}