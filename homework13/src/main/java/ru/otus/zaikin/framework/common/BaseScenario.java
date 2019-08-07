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

import com.codeborne.selenide.Selenide;
import cucumber.api.Scenario;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Supporting Scenario
 */
@Log4j2
public final class BaseScenario {

    private static BaseScenario instance = new BaseScenario();

    private static BaseEnvironment environment;

    private BaseScenario() {
    }

    public static BaseScenario getInstance() {
        return instance;
    }

    public BaseEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(BaseEnvironment BaseEnvironment) {
        environment = BaseEnvironment;
    }

    public static void sleep(int seconds) {
        Selenide.sleep(TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS));
    }


    public BasePage getCurrentPage() {
        return environment.getPages().getCurrentPage();
    }

    public void setCurrentPage(BasePage page) {
        if (page == null) {
            throw new IllegalArgumentException("Unknown page. Check annotation @Name of target page");
        }
        environment.getPages().setCurrentPage(page);
    }

    public static <T extends BasePage> void withPage(Class<T> clazz, Consumer<T> consumer) {
        withPage(clazz, true, consumer);
    }

    public static <T extends BasePage> void withPage(Class<T> clazz, boolean checkIfElementsAppeared, Consumer<T> consumer) {
        Pages.withPage(clazz, checkIfElementsAppeared, consumer);
    }


    public Scenario getScenario() {
        return this.getEnvironment().getScenario();
    }

    public Pages getPages() {
        return this.getEnvironment().getPages();
    }

    public BasePage getPage(String name) {
        return this.getEnvironment().getPage(name);
    }

    public void write(Object object) {
        this.getEnvironment().write(object);
    }

    public Object getVar(String name) {
        Object obj = this.getEnvironment().getVar(name);
        if (obj == null) {
            throw new IllegalArgumentException("Variable " + name + " is not found");
        }
        return obj;
    }

    public Object tryGetVar(String name) {
        return this.getEnvironment().getVar(name);
    }

    public <T extends BasePage> BasePage getPage(Class<T> clazz, boolean checkIfElementsAppeared) {
        return Pages.getPage(clazz, checkIfElementsAppeared).initialize();
    }

    public <T extends BasePage> T getPage(Class<T> clazz) {
        return Pages.getPage(clazz, true);
    }

    public <T extends BasePage> T getPage(Class<T> clazz, String name) {
        return this.getEnvironment().getPage(clazz, name);
    }

    public String replaceVariables(String stringToReplaceIn) {
        return this.getEnvironment().replaceVariables(stringToReplaceIn);
    }

    public void setVar(String name, Object object) {
        this.getEnvironment().setVar(name, object);
    }

    public ScopedVariables getVars() {
        return this.getEnvironment().getVars();
    }
}
