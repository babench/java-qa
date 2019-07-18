package ru.otus.zaikin.otus.personalpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.OtusConfig;
import ru.otus.zaikin.otus.personalpage.block.ContactsBlock;
import ru.otus.zaikin.otus.personalpage.block.PrimaryContactBlock;
import ru.otus.zaikin.otus.personalpage.entity.UserEntity;

@Log4j2
public class PersonalPagePO extends BasePage {
    private PrimaryContactBlock primaryContactBlock;
    private ContactsBlock contactsBlock;

    public PersonalPagePO() {
        primaryContactBlock = new PrimaryContactBlock();
        contactsBlock = new ContactsBlock();
    }

    PersonalPagePO open() {
        log.trace("open");
        driver.get(OtusConfig.URL + "/lk/biography/personal");
        return this;
    }

    public UserEntity getUserData() {
        log.trace("getUserData");
        UserEntity userEntity = new UserEntity();
        primaryContactBlock.readToEntity(userEntity);
        contactsBlock.readToEntity(userEntity);
        return userEntity;
    }


    public PersonalPagePO writeUserData(UserEntity entity) {
        log.trace("writeUserData");
        primaryContactBlock.render(entity);
        contactsBlock.render(entity);
        return this;
    }

    public PersonalPagePO saveFillAllLater() {
        driver.findElement(By.cssSelector(".lk-cv-action-buttons")).findElements(By.cssSelector("button")).get(1).click();
        return this;
    }
}
