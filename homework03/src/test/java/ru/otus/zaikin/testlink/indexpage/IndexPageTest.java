package ru.otus.zaikin.testlink.indexpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class IndexPageTest extends DriverBase {

    private IndexPagePO indexPagePO;
    private WebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        indexPagePO = new IndexPagePO();
        indexPagePO.openSiteAndLogin();
        indexPagePO.init();
    }

    @Test
    public void shouldOpen() {
        assertThat(indexPagePO.frames).hasSize(2).contains("titlebar", "mainframe");
    }
}