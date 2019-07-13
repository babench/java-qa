package ru.otus.zaikin.google.youtube;

import com.lazerycode.selenium.util.Query;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.otus.zaikin.google.BasePage;


@Log4j2
public class UploadVideoPO extends BasePage {
    private Query uploadButton = new Query(By.cssSelector("#upload-prompt-box > div:nth-child(2) > input[type=file]"), driver);

    public UploadVideoPO upload(String fileLocation) {
        createAndGetWait().until(ExpectedConditions.presenceOfElementLocated(uploadButton.locator()));
        uploadButton.findWebElement().sendKeys(fileLocation);
        return this;
    }
}