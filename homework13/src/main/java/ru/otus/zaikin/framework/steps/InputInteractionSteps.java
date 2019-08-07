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
package ru.otus.zaikin.framework.steps;

import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.en.When;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InputInteractionSteps extends BaseMethods {

    @When("^into the field named \"([^\"]*)\" has been typed value \"(.*)\"$")
    public void setFieldValue(String elementName, String value) {
        value = getPropertyOrStringVariableOrValue(value);
        SelenideElement valueInput = baseScenario.getCurrentPage().getElement(elementName);
        cleanField(elementName);
        valueInput.setValue(value);
    }
}