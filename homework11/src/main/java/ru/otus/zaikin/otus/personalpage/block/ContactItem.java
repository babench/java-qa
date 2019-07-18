package ru.otus.zaikin.otus.personalpage.block;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.personalpage.ContactInformationChannel;
import ru.otus.zaikin.otus.personalpage.entity.ContactInfoEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
class ContactItem extends BasePage {

    public static final String ID_CONTACT = "id_contact";

    ContactInfoEntity getEntityById(Integer index) {
        log.trace("getEntityById");
        ContactInfoEntity entity = new ContactInfoEntity();

        String channel = driver.findElement(By.cssSelector("input[name='contact-" + index + "-service")).getAttribute("value");
        entity.setChannel(ContactInformationChannel.valueOf(channel.toUpperCase()));

        String value = getValueElementById(index, "value").getAttribute("value");
        entity.setValue(value);

        boolean preferable = getValueElementById(index, "preferable").isSelected();
        entity.setPreferableChannel(preferable);

        return entity;
    }

    List<Integer> getItemsIdList() {
        List<WebElement> contactIds = driver.findElements(By.cssSelector("input[id^='id_contact-']")).stream().filter(e -> e.getAttribute("id").endsWith("-id")).collect(Collectors.toList());
        return contactIds.stream().map(e -> Integer.valueOf(e.getAttribute("id").replace("id_contact-", "").replace("-id", ""))).collect(Collectors.toList());
    }

    void addItem(ContactInfoEntity entity) {
        log.trace("addItem");
        driver.findElement(By.cssSelector("div[data-prefix='contact']")).findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        Integer index = Collections.max(new ContactItem().getItemsIdList());

        //list of values
        driver.findElement(By.cssSelector("input[name='contact-" + index + "-service")).findElement(By.xpath("../div")).click();
        Optional<WebElement> webElementOptional = getValueElementById(index, "id").findElement(By.xpath("..")).findElements(By.cssSelector("button.js-custom-select-option")).stream().
                filter(e -> entity.getChannel().getSystem().equalsIgnoreCase(e.getAttribute("data-value"))).findFirst();
        WebElement webElement = webElementOptional.orElseThrow(() -> new RuntimeException("Not found"));
        webElement.click();

        //text
        getValueElementById(index, "value").sendKeys(entity.getValue());

        //boolean
        if (entity.isPreferableChannel())
            getValueElementById(index, "preferable").findElement(By.xpath("..")).click();
    }

    private WebElement getValueElementById(Integer index, String field) {
        return driver.findElement(By.id(String.join("-", ID_CONTACT, String.valueOf(index), field)));
    }
}
