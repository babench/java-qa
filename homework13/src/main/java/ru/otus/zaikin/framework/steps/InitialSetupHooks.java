package ru.otus.zaikin.framework.steps;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import ru.otus.zaikin.framework.common.BaseEnvironment;

@Slf4j
public class InitialSetupHooks extends BaseMethods {

    @Before(order = 10)
    public void setScenario(Scenario scenario) {
        baseScenario.setEnvironment(new BaseEnvironment(scenario));
    }
}