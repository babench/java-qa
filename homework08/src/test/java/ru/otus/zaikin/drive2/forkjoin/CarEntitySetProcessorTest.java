package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;

import java.util.List;

@Log4j2
public class CarEntitySetProcessorTest {

    private HibernateDao dao;
    private HibernateFactorySessionHolder sessionHolder = new HibernateFactorySessionHolder(
//            String url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
            //String url = "jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD=";
            System.getProperty("DB_URL", "jdbc:h2:tcp://localhost/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD="),
            System.getProperty("DB_USER", "SA"),
            System.getProperty("DB_PASSWORD", "")
    );


    @BeforeMethod
    public void setUp() {
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);
    }

    @Test
    public void shouldProcessInParallel() {
        log.debug("CarEntitySetProcessorTest.shouldProcessInParallel");
        List<CarEntitySet> list = dao.getAll(CarEntitySet.class);
        CarEntitySetProcessor processor = new CarEntitySetProcessor(new TaskCarEntitySetPopulateDetails(list, 0, list.size(), dao));
        processor.startProcessing();
    }
}