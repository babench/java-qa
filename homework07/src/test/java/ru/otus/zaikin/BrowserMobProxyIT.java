package ru.otus.zaikin;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BrowserMobProxyIT extends DriverBase {
    private static final String URL = "https://ya.ru/";
    private RemoteWebDriver driver;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getBrowserMobProxyEnabledDriver();
    }

    @Test
    public void shouldParseTraffic() {
        Har httpArchive = getHar();
        assertThat(getHTTPStatusCode(URL, httpArchive)).isEqualTo(200);
    }

    @Test
    public void shouldSaveHar() throws IOException {
        Har httpArchive = getHar();
        File file = new File("./log/ya.ru.har.log");
        if (file.exists()) file.delete();
        httpArchive.writeTo(file);
        assertThat(file).exists();
    }

    private Har getHar() {
        getBrowserMobProxy().newHar();
        driver.get(URL);
        return getBrowserMobProxy().getHar();
    }

    private int getHTTPStatusCode(String expectedURL, Har httpArchive) {
        for (HarEntry entry : httpArchive.getLog().getEntries()) {
            if (entry.getRequest().getUrl().equals(expectedURL)) {
                return entry.getResponse().getStatus();
            }
        }
        return 0;
    }
}