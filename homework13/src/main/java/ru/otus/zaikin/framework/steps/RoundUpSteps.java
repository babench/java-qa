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

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.log4j.Log4j2;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class RoundUpSteps extends BaseMethods {

    @When("^waiting for debug$")
    public void waitForDebug() {
        log.debug("here is breakpoint");
    }

    @Then("^variable named \"([^\"]*)\" is equal to variable named \"([^\"]*)\"$")
    public void compareTwoVariables(String firstVariableName, String secondVariableName) {
        String firstValueToCompare = baseScenario.getVar(firstVariableName).toString();
        String secondValueToCompare = baseScenario.getVar(secondVariableName).toString();
        assertThat(firstValueToCompare).isEqualTo(secondValueToCompare);
    }

    @Then("^variable named \"([^\"]*)\" is not equal to variable named \"([^\"]*)\"$")
    public void checkingTwoVariablesAreNotEquals(String firstVariableName, String secondVariableName) {
        String firstValueToCompare = baseScenario.getVar(firstVariableName).toString();
        String secondValueToCompare = baseScenario.getVar(secondVariableName).toString();
        assertThat(firstValueToCompare).isNotEqualTo(secondValueToCompare);
    }
}