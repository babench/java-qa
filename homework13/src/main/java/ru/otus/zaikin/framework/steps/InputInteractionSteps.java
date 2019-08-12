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