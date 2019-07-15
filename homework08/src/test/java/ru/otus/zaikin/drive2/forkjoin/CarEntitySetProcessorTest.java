package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;
import ru.otus.zaikin.drive2.indexpage.IndexPagePO;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.otus.zaikin.framework.DriverBase.getDriver;

@Log4j2
public class CarEntitySetProcessorTest {
    private WebDriver driver;
    private HibernateDao dao;

    @BeforeMethod
    public void setup() throws MalformedURLException {
        HibernateFactorySessionHolder sessionHolder = new HibernateFactorySessionHolder(
                System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", "")
        );
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);
        driver = getDriver();
    }

    @Test
    public void shouldProcessInParallel() {
        log.debug("CarEntitySetProcessorTest.shouldProcessInParallel");
        IndexPagePO indexPagePO = new IndexPagePO(dao);
        indexPagePO.openSite();
        indexPagePO.saveCarsOfBrend("Mazda");
        List<CarEntitySet> list = dao.getAll(CarEntitySet.class);
        assertThat(list.size()).isGreaterThan(100);
        List<CarEntitySet> filtered = list.stream().filter(e -> e.getPrice() == null).limit(100).collect(Collectors.toList());
        CarEntitySetProcessor processor = new CarEntitySetProcessor(new TaskCarEntitySetPopulateDetails(filtered, 0, filtered.size(), dao));
        processor.startProcessing();
        DriverBase.closeDriverObjects();
    }
}