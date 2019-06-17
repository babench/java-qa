package ru.otus.zaikin.testlink.indexpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.otus.zaikin.testlink.BasePage;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class IndexPagePO extends BasePage {
    List<String> frames;

    public void init() {
        readFrames();
    }

    private void readFrames() {
        log.debug("IndexPagePO.readFrames:start");
        driver.switchTo().defaultContent();
        frames = new ArrayList<>();
        List<WebElement> frameList = driver.findElements(By.cssSelector("frame"));
        frameList.forEach(f -> frames.add(f.getAttribute("name")));
        log.debug("IndexPagePO.readFrames:end");
    }
}