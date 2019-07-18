package ru.otus.zaikin.otus.personalpage.block;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.personalpage.entity.UserEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class ContactsBlock extends BasePage {

    public void render(UserEntity entity) {
        log.debug("render");
        log.debug("clear old contacts");
        Comparator c = Collections.reverseOrder();
        new ContactItem().getItemsIdList().stream().sorted(c).forEach(e ->
                driver.findElement(By.xpath("//*[@id='id_contact-" + e + "-id']/..")).findElement(By.cssSelector(".js-formset-delete")).click()
        );
        log.debug("add new contact");
        entity.getContactInfoEntities().forEach(p -> new ContactItem().addItem(p));
    }

    public void readToEntity(UserEntity entity) {
        List<Integer> ids = new ContactItem().getItemsIdList();
        ids.forEach(i -> entity.contactInfoEntities.add(new ContactItem().getEntityById(i)));
    }
}
