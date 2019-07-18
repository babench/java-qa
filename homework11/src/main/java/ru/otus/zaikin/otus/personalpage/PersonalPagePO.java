package ru.otus.zaikin.otus.personalpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.OtusConfig;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class PersonalPagePO extends BasePage {
    private By firstNameLocator = By.name("fname");
    private By firstNameLatinLocator = By.name("fname_latin");
    private By lastNameLocator = By.name("lname");
    private By lastNameLatinLocator = By.name("lname_latin");
    private By blogNameLocator = By.name("blog_name");

    PersonalPagePO open() {
        log.trace("open");
        driver.get(OtusConfig.URL + "/lk/biography/personal");
        return this;
    }

    public UserEntity getUserData() {
        log.trace("getUserData");
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(driver.findElement(firstNameLocator).getAttribute("value"));
        userEntity.setFirstNameLatin(driver.findElement(firstNameLatinLocator).getAttribute("value"));
        userEntity.setLastName(driver.findElement(lastNameLocator).getAttribute("value"));
        userEntity.setLastNameLatin(driver.findElement(lastNameLatinLocator).getAttribute("value"));
        userEntity.setBlogName(driver.findElement(blogNameLocator).getAttribute("value"));
        List<Integer> ids = getContactsId();

        for (Integer id : ids) {
            ContactInfoEntity contactInfoEntity = getContactInfoEntityById(id);
            userEntity.contactInfoEntities.add(contactInfoEntity);
        }
        return userEntity;
    }

    private List<Integer> getContactsId() {
        List<WebElement> contactIds = driver.findElements(By.cssSelector("input[id^='id_contact-']")).stream().filter(e -> e.getAttribute("id").endsWith("-id")).collect(Collectors.toList());
        return contactIds.stream().map(e -> Integer.valueOf(e.getAttribute("id").replace("id_contact-", "").replace("-id", ""))).collect(Collectors.toList());
    }

    private ContactInfoEntity getContactInfoEntityById(Integer id) {
        log.trace("getContactInfoEntityById");
        String channel = driver.findElement(By.cssSelector("input[name='contact-" + id + "-service")).getAttribute("value");
        String value = driver.findElement(By.cssSelector("input[id='id_contact-" + id + "-value")).getAttribute("value");
        boolean preferable = driver.findElement(By.cssSelector("input[id='id_contact-" + id + "-preferable")).isSelected();
        ContactInfoEntity contactInfoEntity = new ContactInfoEntity();
        contactInfoEntity.setChannel(ContactInformationChannel.valueOf(channel.toUpperCase()));
        contactInfoEntity.setValue(value);
        contactInfoEntity.setPreferableChannel(preferable);
        return contactInfoEntity;
    }

    public PersonalPagePO writeUserData(UserEntity myself) {
        log.trace("writeUserData");

        driver.findElement(firstNameLocator).clear();
        driver.findElement(firstNameLocator).sendKeys(myself.getFirstName());

        driver.findElement(firstNameLatinLocator).clear();
        driver.findElement(firstNameLatinLocator).sendKeys(myself.getFirstNameLatin());

        driver.findElement(lastNameLocator).clear();
        driver.findElement(lastNameLocator).sendKeys(myself.getLastName());

        driver.findElement(lastNameLatinLocator).clear();
        driver.findElement(lastNameLatinLocator).sendKeys(myself.getLastNameLatin());

        driver.findElement(blogNameLocator).clear();
        driver.findElement(blogNameLocator).sendKeys(myself.getBlogName());

        log.debug("clear old contacts");
        List<Integer> contactsId = getContactsId();
        log.debug(contactsId);
        Comparator c = Collections.reverseOrder();
        contactsId.stream().sorted(c).forEach(e ->
                driver.findElement(By.xpath("//*[@id='id_contact-" + e + "-id']/..")).findElement(By.cssSelector(".js-formset-delete")).click()
        );

        log.debug("add new contact");
        myself.getContactInfoEntities().forEach(this::addContact);
        return this;
    }

    private void addContact(ContactInfoEntity contactInfoEntity) {
        log.trace("addContact");
        driver.findElement(By.cssSelector("div[data-prefix='contact']")).findElement(By.cssSelector("button.js-lk-cv-custom-select-add")).click();
        Integer index = Collections.max(getContactsId());

        driver.findElement(By.cssSelector("input[name='contact-" + index + "-service")).findElement(By.xpath("../div")).click();
        Optional<WebElement> webElementOptional = driver.findElement(By.id("id_contact-" + index + "-id")).findElement(By.xpath("..")).findElements(By.cssSelector("button.js-custom-select-option")).stream().
                filter(e -> contactInfoEntity.channel.getSystem().equalsIgnoreCase(e.getAttribute("data-value"))).findFirst();
        WebElement webElement = webElementOptional.orElseThrow(() -> new RuntimeException("Not found"));
        webElement.click();

        driver.findElement(By.id("id_contact-" + index + "-value")).sendKeys(contactInfoEntity.getValue());

        if (contactInfoEntity.isPreferableChannel())
            driver.findElement(By.id("id_contact-" + index + "-preferable")).findElement(By.xpath("..")).click();
    }

    public PersonalPagePO saveFillAllLater() {
        driver.findElement(By.cssSelector(".lk-cv-action-buttons")).findElements(By.cssSelector("button")).get(1).click();
        return this;
    }
}
