package ru.otus.zaikin;


import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.openqa.selenium.WebDriver;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.forkjoin.CarEntitySetProcessor;
import ru.otus.zaikin.drive2.forkjoin.TaskCarEntitySetPopulateDetails;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;
import ru.otus.zaikin.drive2.indexpage.IndexPagePO;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.List;

@Log4j2
public class Application {
    private IndexPagePO indexPagePO;
    private HibernateDao dao;
    private HibernateFactorySessionHolder sessionHolder;

    public static void main(String[] args) throws MalformedURLException {
        log.debug("Application.main");
        new Application().start();
    }

    public void start() throws MalformedURLException {
        DriverBase.instantiateDriverObject();

        sessionHolder = new HibernateFactorySessionHolder(
                //System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1")
                System.getProperty("DB_URL", "jdbc:h2:tcp://192.168.99.100:1521/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD="),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", "")
        );
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);
        log.debug("hibernate started");

        WebDriver driver = DriverBase.getDriver();
        indexPagePO = new IndexPagePO(dao);
        indexPagePO.openSite();
        //read mazda cards
        indexPagePO.saveCarsOfBrend("Mazda");
        List<CarEntitySet> list = dao.getAll(CarEntitySet.class);
        //process all records in parallel
        CarEntitySetProcessor processor = new CarEntitySetProcessor(new TaskCarEntitySetPopulateDetails(list, 0, list.size(), dao));
        processor.startProcessing();
        DriverBase.closeDriverObjects();
        log.debug("export dataset");

        log.debug("processing completed!");

    }
}
