package ru.otus.zaikin.logger.channel;

import lombok.extern.log4j.Log4j2;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import ru.otus.zaikin.AppResources;
import ru.otus.zaikin.logger.TestResultChannel;

import java.util.Properties;

@Log4j2
public class InfluxDBChannel implements TestResultChannel {

    private final InfluxDB inflxudb;
    private final String database;

    public InfluxDBChannel() {
        Properties properties = new AppResources().loadPropertiesFile("config.properties");
        this.inflxudb = InfluxDBFactory.connect("http://192.168.99.100:8086", "root", "root");
        this.database = "test";
    }

    @Override
    public void send(Point point) {
        log.debug("InfluxDB channel send " + point);
    }
}
