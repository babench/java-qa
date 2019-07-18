package ru.otus.zaikin.otus.personalpage.block;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import ru.otus.zaikin.otus.BasePage;
import ru.otus.zaikin.otus.personalpage.entity.UserEntity;

@Log4j2
public class PrimaryContactBlock extends BasePage {

    private By firstNameLocator = By.name("fname");
    private By firstNameLatinLocator = By.name("fname_latin");
    private By lastNameLocator = By.name("lname");
    private By lastNameLatinLocator = By.name("lname_latin");
    private By blogNameLocator = By.name("blog_name");

    public void readToEntity(UserEntity userEntity) {
        userEntity.setFirstName(driver.findElement(firstNameLocator).getAttribute("value"));
        userEntity.setFirstNameLatin(driver.findElement(firstNameLatinLocator).getAttribute("value"));
        userEntity.setLastName(driver.findElement(lastNameLocator).getAttribute("value"));
        userEntity.setLastNameLatin(driver.findElement(lastNameLatinLocator).getAttribute("value"));
        userEntity.setBlogName(driver.findElement(blogNameLocator).getAttribute("value"));
    }

    public void render(UserEntity entity) {
        driver.findElement(firstNameLocator).clear();
        driver.findElement(firstNameLocator).sendKeys(entity.getFirstName());

        driver.findElement(firstNameLatinLocator).clear();
        driver.findElement(firstNameLatinLocator).sendKeys(entity.getFirstNameLatin());

        driver.findElement(lastNameLocator).clear();
        driver.findElement(lastNameLocator).sendKeys(entity.getLastName());

        driver.findElement(lastNameLatinLocator).clear();
        driver.findElement(lastNameLatinLocator).sendKeys(entity.getLastNameLatin());

        driver.findElement(blogNameLocator).clear();
        driver.findElement(blogNameLocator).sendKeys(entity.getBlogName());
    }
}
