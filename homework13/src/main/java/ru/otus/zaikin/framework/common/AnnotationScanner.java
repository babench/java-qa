package ru.otus.zaikin.framework.common;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotationScanner {
    private static Reflections reflection = new Reflections("ru.otus.zaikin");

    public Set<Class<?>> getClassesAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflection.getTypesAnnotatedWith(annotation);
    }
}