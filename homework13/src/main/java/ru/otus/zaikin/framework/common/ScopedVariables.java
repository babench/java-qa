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

import com.google.common.collect.Maps;
import com.google.gson.JsonParser;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.otus.zaikin.framework.utils.PropertyLoader.loadProperty;

@Log4j2
public class ScopedVariables {
    public static final String VARIABLE_NAME_PATTERN = "[{]([\\wa-zA-Z]+[\\wa-zA-Z.-]+[\\wa-zA-Z]+)[}]";
    private Map<String, Object> variables = Maps.newHashMap();

    public String replaceVariables(String textToReplaceIn) {
        Pattern p = Pattern.compile(VARIABLE_NAME_PATTERN);
        Matcher m = p.matcher(textToReplaceIn);
        StringBuffer buffer = new StringBuffer();
        while (m.find()) {
            String varName = m.group(1);
            String value = get(varName).toString();
            m.appendReplacement(buffer, value);
        }
        m.appendTail(buffer);
        return buffer.toString();
    }

    public static String resolveVars(String inputString) {
        Pattern p = Pattern.compile(VARIABLE_NAME_PATTERN);
        Matcher m = p.matcher(inputString);
        String newString = "";
        List<String> unresolvedVariables = new ArrayList<>();
        while (m.find()) {
            String varName = m.group(1);
            String value = loadProperty(varName, (String) BaseScenario.getInstance().tryGetVar(varName));
            if (value == null) {
                unresolvedVariables.add(varName);
                value = varName;
            }
            newString = m.replaceFirst(value);
            m = p.matcher(newString);
        }
        if (!unresolvedVariables.isEmpty()) {
            throw new IllegalArgumentException(
                    "Value " + unresolvedVariables +
                            " not found in application.properties and environment variable");
        }
        if (newString.isEmpty()) {
            newString = inputString;
        }
        return newString;
    }

    public static boolean isJSONValid(String jsonInString) {
        try {
            JsonParser parser = new JsonParser();
            parser.parse(jsonInString);
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
        return true;
    }

    public void put(String name, Object value) {
        variables.put(name, value);
    }

    public Object get(String name) {
        return variables.get(name);
    }

    public void clear() {
        variables.clear();
    }

    public Object remove(String key) {
        return variables.remove(key);
    }

}
