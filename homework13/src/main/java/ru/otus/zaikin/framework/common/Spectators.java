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

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.Collection;

public final class Spectators {

    private Spectators() {

    }

    public static void waitElementsUntil(Condition selenideCondition, int timeout, SelenideElement... selenideElements) {
        Arrays.stream(selenideElements).forEach(e -> e.waitUntil(selenideCondition, timeout));
    }

    public static void waitElementsUntil(Condition selenideCondition, int timeout, Collection<SelenideElement> selenideElements) {
        selenideElements.forEach(e -> e.waitUntil(selenideCondition, timeout));
    }

}
