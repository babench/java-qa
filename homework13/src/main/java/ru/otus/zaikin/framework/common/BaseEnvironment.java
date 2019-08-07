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
package ru.otus.zaikin.framework.common;

import cucumber.api.Scenario;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.otus.zaikin.framework.annotations.Name;

import java.util.Arrays;

/**
 * Stores pages and variables in scenario
 */
@Slf4j
public class BaseEnvironment {
    private Scenario scenario;
    private ThreadLocal<ScopedVariables> variables = new ThreadLocal<>();
    private Pages pages = new Pages();

    public BaseEnvironment(Scenario scenario) {
        this.scenario = scenario;
        initPages();
    }

    public BaseEnvironment() {
        initPages();
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    private void initPages() {
        new AnnotationScanner().getClassesAnnotatedWith(Name.class)
                .stream()
                .map(it -> {
                    if (BasePage.class.isAssignableFrom(it)) {
                        return (Class<? extends BasePage>) it;
                    } else {
                        throw new IllegalStateException("Class " + it.getName() + " should extends BasePage");
                    }
                })
                .forEach(clazz -> pages.put(getClassAnnotationValue(clazz), clazz));
    }


    private String getClassAnnotationValue(Class<?> c) {
        return Arrays.stream(c.getAnnotationsByType(Name.class))
                .findAny()
                .map(Name::value)
                .orElseThrow(() -> new AssertionError("Not found annotation BasePage.Name for " + c.getClass().getName()));
    }

    /**
     * Logging
     */
    public void write(Object object) {
        scenario.write(String.valueOf(object));
    }

    public ScopedVariables getVars() {
        return getVariables();
    }

    public Object getVar(String name) {
        return getVariables().get(name);
    }

    public void setVar(String name, Object object) {
        getVariables().put(name, object);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public Pages getPages() {
        return pages;
    }

    public BasePage getPage(String name) {
        return pages.get(name);
    }

    public <T extends BasePage> T getPage(Class<T> clazz, String name) {
        return pages.get(clazz, name);
    }

    public String replaceVariables(String textToReplaceIn) {
        return getVariables().replaceVariables(textToReplaceIn);
    }

    private ScopedVariables getVariables() {
        if (variables.get() == null) {
            variables.set(new ScopedVariables());
        }
        return variables.get();
    }
}