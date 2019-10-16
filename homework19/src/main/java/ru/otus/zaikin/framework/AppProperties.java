package ru.otus.zaikin.framework;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
public enum AppProperties {
    APP_PROPERTIES;

    private final Properties prop;

    public static AppProperties getInstance() {
        return APP_PROPERTIES;
    }

    AppProperties() {
        prop = readProperties();
    }

    private final static Properties readProperties() {
        Properties prop = new Properties();
        try (InputStream resourceAsStream = AppProperties.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(resourceAsStream);
        } catch (IOException e) {
            log.error("error", e);
        }
        return prop;
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public Properties getProperties() {
        return prop;
    }
}