package ru.otus.zaikin.framework.steps;

import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static ru.otus.zaikin.framework.utils.PropertyLoader.loadValueFromFileOrPropertyOrVariableOrDefault;


@Log4j2
public class ElementsInteractionSteps extends BaseMethods {

    @And("^pressed (?:button|field|block) named \"([^\"]*)\"$")
    public void clickOnElement(String elementName) {
        baseScenario.getCurrentPage().getElement(elementName).click();
    }

    @When("^value from the (?:element|field) named \"([^\"]*)\" has been saved to the variable named \"([^\"]*)\"$")
    public void storeElementValueInVariable(String elementName, String variableName) {
        baseScenario.setVar(variableName, baseScenario.getCurrentPage().getAnyElementText(elementName));
        baseScenario.write("Value [" + baseScenario.getCurrentPage().getAnyElementText(elementName) + "] saved into variable [" + variableName + "]");
    }

    @When("^hovered (?:field|element) named \"([^\"]*)\"$")
    public void elementHover(String elementName) {
        SelenideElement field = baseScenario.getCurrentPage().getElement(elementName);
        field.hover();
    }

    @And("^clicked on element with text \"(.*)\"$")
    public void findElement(String text) {
        $(By.xpath(getTranslateNormalizeSpaceText(getPropertyOrStringVariableOrValue(text)))).click();
    }

    @When("^clicked on button named \"([^\"]*)\" and file named \"([^\"]*)\" has been loaded$")
    public void clickOnButtonAndUploadFile(String buttonName, String fileName) {
        String file = loadValueFromFileOrPropertyOrVariableOrDefault(fileName);
        File attachmentFile = new File(file);
        baseScenario.getCurrentPage().getElement(buttonName).uploadFile(attachmentFile);
    }
}