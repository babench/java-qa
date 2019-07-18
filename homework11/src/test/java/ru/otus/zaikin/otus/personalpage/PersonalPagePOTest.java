package ru.otus.zaikin.otus.personalpage;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.otus.loginpage.LoginPagePO;
import ru.otus.zaikin.otus.mainpage.MainPagePO;

import java.net.MalformedURLException;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class PersonalPagePOTest extends DriverBase {

    private WebDriver driver;
    private MainPagePO mainPagePO;
    private LoginPagePO loginPagePO;
    private PersonalPagePO personalPagePO;
    private UserEntity myself;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = getDriver();
        mainPagePO = new MainPagePO();
        myself = new UserEntity();
        fillMyProfileEntity();
    }

    private void fillMyProfileEntity() {
        myself.setFirstName("Александр");
        myself.setFirstNameLatin("Alexander");
        myself.setLastName("Заикин");
        myself.setLastNameLatin("Zaikin");
        myself.setBlogName("Allex");
        myself.getContactInfoEntities().add(new ContactInfoEntity(ContactInformationChannel.SKYPE, "skype value", true));
        myself.getContactInfoEntities().add(new ContactInfoEntity(ContactInformationChannel.VK, "VK value", false));
    }

    @Test
    public void shouldOpen() {
        log.debug("BasePageTest.shouldOpen");
        openPersonalPage();
        log.debug("here");
    }

    @Test
    public void shouldReadUserData() {
        openPersonalPage();
        personalPagePO.writeUserData(myself);
        personalPagePO.saveFillAllLater();

        mainPagePO.openPersonalPage();
        UserEntity userEntity = personalPagePO.getUserData();
        log.debug(userEntity);
        assertThat(userEntity.getContactInfoEntities().get(0).getChannel()).isEqualTo(ContactInformationChannel.SKYPE);
        assertThat(userEntity.getContactInfoEntities().get(1).getChannel()).isEqualTo(ContactInformationChannel.VK);
        assertThat(userEntity).isEqualTo(myself);
    }

    @Test
    public void shouldWriteUserData() {
        openPersonalPage();
        personalPagePO.writeUserData(myself);
        personalPagePO.saveFillAllLater();
    }

    private void openPersonalPage() {
        loginPagePO = mainPagePO.openSite().openLoginPage();
        String userId = System.getProperty("otus.user.id");
        String userPassword = System.getProperty("otus.user.password");
        loginPagePO.login(userId, userPassword);
        log.debug(driver.findElement(By.cssSelector(".ic-blog-default-avatar")).getAttribute("style"));
        personalPagePO = mainPagePO.openPersonalPage();
    }
}