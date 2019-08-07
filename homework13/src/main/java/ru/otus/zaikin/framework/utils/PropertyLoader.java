/**
 * Copyright 2017 Alfa Laboratory
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.otus.zaikin.framework.utils;

import com.google.common.base.Strings;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import ru.otus.zaikin.framework.common.BaseScenario;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


@Log4j2
public class PropertyLoader {
    private static final String PROPERTIES_FILE = "/application.properties";
    private static final Properties PROPERTIES = getPropertiesInstance();
    private static final Properties PROFILE_PROPERTIES = getProfilePropertiesInstance();

    private PropertyLoader() {

    }

    public static String loadSystemPropertyOrDefault(String propertyName, String defaultValue) {
        String propValue = System.getProperty(propertyName);
        return propValue != null ? propValue : defaultValue;
    }

    public static Integer loadSystemPropertyOrDefault(String propertyName, Integer defaultValue) {
        try {
            return Integer.valueOf(System.getProperty(propertyName, defaultValue.toString()).trim());
        } catch (NumberFormatException ex) {
            log.error("Could not parse value to Integer ", ex.getMessage());
            return defaultValue;
        }
    }


    public static Boolean loadSystemPropertyOrDefault(String propertyName, Boolean defaultValue) {
        String def = defaultValue.toString();
        String property = loadProperty(propertyName, def);
        return Boolean.parseBoolean(property.trim());
    }

    public static String loadProperty(String propertyName) {
        String value = tryLoadProperty(propertyName);
        if (null == value)
            throw new IllegalArgumentException("In application.properties not found by key: " + propertyName);
        return value;
    }

    public static String getPropertyOrValue(String propertyNameOrValue) {
        return loadProperty(propertyNameOrValue, propertyNameOrValue);
    }

    public static String loadProperty(String propertyName, String defaultValue) {
        String value = tryLoadProperty(propertyName);
        return value != null ? value : defaultValue;
    }

    public static Integer loadPropertyInt(String propertyName, Integer defaultValue) {
        String value = tryLoadProperty(propertyName);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }


    public static String tryLoadProperty(String propertyName) {
        String value = null;
        if (!Strings.isNullOrEmpty(propertyName)) {
            String systemProperty = loadSystemPropertyOrDefault(propertyName, propertyName);
            if (!propertyName.equals(systemProperty)) return systemProperty;
            value = PROFILE_PROPERTIES.getProperty(propertyName);
            if (null == value) {
                value = PROPERTIES.getProperty(propertyName);
            }
        }
        return value;
    }

    @SneakyThrows(IOException.class)
    private static Properties getPropertiesInstance() {
        Properties instance = new Properties();
        try (
                InputStream resourceStream = PropertyLoader.class.getResourceAsStream(PROPERTIES_FILE);
                InputStreamReader inputStream = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)
        ) {
            instance.load(inputStream);
        }
        return instance;
    }

    @SneakyThrows(IOException.class)
    private static Properties getProfilePropertiesInstance() {
        Properties instance = new Properties();
        String profile = System.getProperty("profile", "");
        if (!Strings.isNullOrEmpty(profile)) {
            String path = Paths.get(profile).toString();
            URL url = PropertyLoader.class.getClassLoader().getResource(path);
            try (
                    InputStream resourceStream = url.openStream();
                    InputStreamReader inputStream = new InputStreamReader(resourceStream, StandardCharsets.UTF_8)
            ) {
                instance.load(inputStream);
            }
        }
        return instance;
    }

    public static String loadValueFromFileOrPropertyOrVariableOrDefault(String valueToFind) {
        String pathAsString = StringUtils.EMPTY;
        String propertyValue = tryLoadProperty(valueToFind);
        if (StringUtils.isNotBlank(propertyValue)) {
            BaseScenario.getInstance().write("Variable " + valueToFind + " from application.properties = " + propertyValue);
            return propertyValue;
        }
        try {
            Path path = Paths.get(System.getProperty("user.dir") + valueToFind);
            pathAsString = path.toString();
            String fileValue = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            BaseScenario.getInstance().write("Value from file " + valueToFind + " = " + fileValue);
            return fileValue;
        } catch (IOException | InvalidPathException e) {
            BaseScenario.getInstance().write("Value not found by path " + pathAsString);
        }
        if (BaseScenario.getInstance().tryGetVar(valueToFind) != null)
            return (String) BaseScenario.getInstance().getVar(valueToFind);
        BaseScenario.getInstance().write("Value not found. Will use default " + valueToFind);
        return valueToFind;
    }

}

