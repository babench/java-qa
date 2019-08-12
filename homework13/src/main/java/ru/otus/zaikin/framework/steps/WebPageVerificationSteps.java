package ru.otus.zaikin.framework.steps;

import cucumber.api.java.en.Then;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class WebPageVerificationSteps extends BaseMethods {

    @Then("^(?:page|block|form|tab) \"([^\"]*)\" has been loaded$")
    public void loadingPage(String nameOfPage) {
        super.loadPage(nameOfPage);
    }


}