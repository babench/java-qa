package ru.otus.zaikin.drive2.carpage;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;

@Log4j2
public class CarPageTest extends DriverBase {

    private WebDriver driver;
    private CarPagePO carPagePO;


    @BeforeMethod
    public void setUp() throws MalformedURLException {
        System.out.println("CarPageTest.setUp");
        HibernateFactorySessionHolder sessionHolder = new HibernateFactorySessionHolder(
                System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", "")
        );
        Session session = sessionHolder.openSession();
        driver = getDriver();
        HibernateDao dao = new HibernateDao(session);
        carPagePO = new CarPagePO(dao);
    }

    @Test
    public void shouldOpenCarById() {
        log.debug("CarPageTest.shouldOpenCarById");
        carPagePO.openCarById(11L);
        System.out.println("here");
    }

    @Test
    public void shouldReadUSD() {
        long id = 6L;
        carPagePO.openCarById(id).readFieldsAndUpdateEntity(id);
        log.debug("here");
    }

    @Test
    public void shouldReadRUR() {
        long id = 11L;
        carPagePO.openCarById(id).readFieldsAndUpdateEntity(id);
        System.out.println("here");
    }
}