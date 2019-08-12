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