package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import ru.otus.zaikin.logger.BaseTest;
import ru.otus.zaikin.logger.CustomTestWatcher;

@Log4j2
public class OtusTest extends BaseTest {

    @Rule
    public CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Test
    public void shouldOpenSite() {
        log.debug("shouldOpenSite:start");
        getDriver().get("https://otus.ru");
        Assert.assertEquals("OTUS - Онлайн-образование", getDriver().getTitle());
        log.debug("shouldOpenSite:end");
    }
}
