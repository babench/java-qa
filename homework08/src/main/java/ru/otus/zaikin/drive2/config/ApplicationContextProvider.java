package ru.otus.zaikin.drive2.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextProvider {
    private ApplicationContextProvider() {
    }

    public static AnnotationConfigApplicationContext getInstance() {
        return HelperHolder.INSTANCE;
    }

    private static class HelperHolder {
        private static final AnnotationConfigApplicationContext INSTANCE =
                new AnnotationConfigApplicationContext();
    }
}