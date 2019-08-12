package ru.otus.zaikin.framework.utils;

import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;

@Log4j2
public final class Reflection {
    private Reflection() {
    }

    public static Object extractFieldValue(Field field, Object owner) {
        field.setAccessible(true);
        try {
            return field.get(owner);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }
}
