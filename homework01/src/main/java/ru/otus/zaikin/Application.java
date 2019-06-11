package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;

@Log4j2
public class Application {

    public static void main(String[] args) {
        log.debug("Application.main");
        Properties prop = new AppResources().loadPropertiesFile("config.properties");
        log.debug("Properties are:");
        prop.forEach((k, v) -> log.debug(k + ":" + v));

        log.debug("check");
        log.debug(prop.getProperty("tests.status.severity"));
        log.debug(prop.getProperty("tests.status.channel"));
    }
}
