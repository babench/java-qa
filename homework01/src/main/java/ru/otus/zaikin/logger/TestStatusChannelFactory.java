package ru.otus.zaikin.logger;

import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.AppProperties;
import ru.otus.zaikin.logger.channel.ConsoleChannel;
import ru.otus.zaikin.logger.channel.InfluxDBChannel;
import ru.otus.zaikin.logger.channel.SlackChannel;

@Log4j2
public class TestStatusChannelFactory {
    public TestStatusChannel get() {
        String channel = AppProperties.getInstance().getProperty("tests.status.channel");
        if (channel == null) throw new IllegalArgumentException("tests.status.channel");
        TestStatusChannelType testStatusChannelType = TestStatusChannelType.valueOf(channel);
        log.debug(testStatusChannelType);

        TestStatusChannel testStatusChannel;
        switch (testStatusChannelType) {
            case CONSOLE:
                testStatusChannel = new ConsoleChannel();
                break;
            case INFLUXDB:
                testStatusChannel = new InfluxDBChannel();
                break;
            case SLACK:
                testStatusChannel = new SlackChannel();
                break;
            default:
                throw new RuntimeException("Non supported channel " + testStatusChannelType);
        }
        return testStatusChannel;
    }
}