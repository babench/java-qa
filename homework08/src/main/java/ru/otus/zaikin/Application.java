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
import ru.otus.zaikin.framework.utils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class Application {
    public static final String BREND = "Mazda";
    private IndexPagePO indexPagePO;
    private HibernateDao dao;
    private HibernateFactorySessionHolder sessionHolder;

    public static void main(String[] args) throws IOException {
        log.debug("Application.main");
        new Application().start();
    }

    public void start() throws IOException {
        DriverBase.instantiateDriverObject();

        sessionHolder = new HibernateFactorySessionHolder(
                System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                //System.getProperty("DB_URL", "jdbc:h2:tcp://192.168.99.100:1521/~/test;DB_CLOSE_DELAY=-1;USER=SA;PASSWORD="),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", "")
        );
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);
        log.debug("hibernate started");
        List<CarEntitySet> list;

        WebDriver driver = DriverBase.getDriver();
        indexPagePO = new IndexPagePO(dao);
        indexPagePO.openSite();
        //read mazda cards
        indexPagePO.saveCarsOfBrend(BREND);
        list = dao.getAll(CarEntitySet.class);
        //process all records in parallel
        CarEntitySetProcessor processor = new CarEntitySetProcessor(new TaskCarEntitySetPopulateDetails(list, 0, list.size(), dao));
        processor.startProcessing();

        DriverBase.closeDriverObjects();
        writeToCsvFile(dao.getAll(CarEntitySet.class), "./log/"+BREND+".csv");
        log.debug("processing completed!");

    }

    private void writeToCsvFile(List<CarEntitySet> list, String csvFile) throws IOException {
        log.debug("Application.writeToCsvFile");
        FileWriter writer = new FileWriter(csvFile);
        CSVUtils.writeLine(writer, Arrays.asList("Id", "CarBrend", "Model", "EngineType", "EnginePower", "EngineVolume", "IssueDate", "Currency", "Price", "Href"));
        for (CarEntitySet c : list) {
            List<String> record = new ArrayList<>();
            record.add(String.valueOf(c.getId()));
            record.add(c.getCarBrend());
            record.add(c.getModel());
            record.add(c.getEngineType());
            record.add(String.valueOf(c.getEnginePower()));
            record.add(String.valueOf(c.getEngineVolume()));
            record.add(String.valueOf(c.getIssueDate()));
            record.add(c.getCurrency());
            record.add(String.valueOf(c.getPrice()));
            record.add(c.getHref());
            CSVUtils.writeLine(writer, record, ';');
        }
        writer.flush();
        writer.close();
    }
}
