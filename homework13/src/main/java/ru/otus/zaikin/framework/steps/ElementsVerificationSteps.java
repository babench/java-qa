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

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import cucumber.api.java.en.Then;
import lombok.extern.log4j.Log4j2;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class ElementsVerificationSteps extends BaseMethods {


    @Then("^value of the (?:field|element) named \"([^\"]*)\" is equal to variable named \"([^\"]*)\"$")
    public void compareFieldAndVariable(String elementName, String variableName) {
        String actualValue = baseScenario.getCurrentPage().getAnyElementText(elementName);
        String expectedValue = baseScenario.getVar(variableName).toString();
        assertThat(actualValue).isEqualTo(expectedValue);
    }


    @Then("^(?:field|element) named \"([^\"]*)\" is clickable$")
    public void clickableField(String elementName) {
        SelenideElement element = baseScenario.getCurrentPage().getElement(elementName);
        assertThat(element.isEnabled()).isTrue();
    }

    @Then("^element named \"([^\"]*)\" contains attribute named \"([^\"]*)\" with value of \"(.*)\"$")
    public void checkElemContainsAtrWithValue(String elementName, String attribute, String expectedAttributeValue) {
        expectedAttributeValue = getPropertyOrStringVariableOrValue(expectedAttributeValue);
        SelenideElement currentElement = baseScenario.getCurrentPage().getElement(elementName);
        String currentAtrValue = currentElement.attr(attribute);
        assertThat(currentAtrValue).isEqualTo(expectedAttributeValue);
    }


    @Then("^(?:field|element) named \"([^\"]*)\" contains value of \"(.*)\"$")
    public void testActualValueContainsSubstring(String elementName, String expectedValue) {
        expectedValue = getPropertyOrStringVariableOrValue(expectedValue);
        String actualValue = baseScenario.getCurrentPage().getAnyElementText(elementName);
        assertThat(actualValue).contains(expectedValue);
    }

    @Then("^(?:field|element) named \"([^\"]*)\" contains inner text \"(.*)\"$")
    public void testFieldContainsInnerText(String fieldName, String expectedText) {
        expectedText = getPropertyOrStringVariableOrValue(expectedText);
        String field = baseScenario.getCurrentPage().getElement(fieldName).innerText().trim().toLowerCase();
        assertThat(field).containsIgnoringCase(expectedText);
    }

    @Then("^value of (?:field|element) named \"([^\"]*)\" is equal to \"(.*)\"$")
    public void compareValInFieldAndFromStep(String elementName, String expectedValue) {
        expectedValue = getPropertyOrStringVariableOrValue(expectedValue);
        String actualValue = baseScenario.getCurrentPage().getAnyElementText(elementName);
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Then("^(?:link|button|field|element) named \"([^\"]*)\" is not (?:clickable|editable)$")
    public void fieldIsDisable(String elementName) {
        SelenideElement element = baseScenario.getCurrentPage().getElement(elementName);
        assertThat(element.is(Condition.disabled)).isTrue();
    }

    @Then("^field named \"([^\"]*)\" contains (\\d+) symbols$")
    public void checkFieldSymbolsCount(String element, int num) {
        int length = baseScenario.getCurrentPage().getAnyElementText(element).length();
        assertThat(length).isEqualTo(num);
    }

    @Then("^field named \"([^\"]*)\" is empty$")
    public void fieldInputIsEmpty(String fieldName) {
        assertThat(baseScenario.getCurrentPage().getAnyElementText(fieldName)).isNullOrEmpty();
    }
}