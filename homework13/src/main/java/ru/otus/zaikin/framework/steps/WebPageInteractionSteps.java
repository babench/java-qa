package ru.otus.zaikin.framework.steps;

import cucumber.api.java.en.When;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.open;
import static ru.otus.zaikin.framework.common.ScopedVariables.resolveVars;
import static ru.otus.zaikin.framework.utils.PropertyLoader.loadProperty;

@Log4j2
public class WebPageInteractionSteps extends BaseMethods {

    @When("^opened page \"([^\"]*)\" by link \"([^\"]*)\"$")
    public void goToSelectedPageByLink(String pageName, String urlOrName) {
        String address = loadProperty(urlOrName, resolveVars(urlOrName));
        baseScenario.write(" url = " + address);
        open(address);
        loadPage(pageName);
    }

    @When("^user \"([^\"]*)\" entered login and password$")
    public void loginByUserData(String userCode) {
        String login = loadProperty(userCode + ".login");
        String password = loadProperty(userCode + ".password");
        cleanField("userName");
        baseScenario.getCurrentPage().getElement("userName").sendKeys(login);
        cleanField("userPassword");
        baseScenario.getCurrentPage().getElement("userPassword").sendKeys(password);
        baseScenario.getCurrentPage().getElement("loginButton").click();
    }
}