package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.zaikin.drive2.config.ApplicationContextProvider;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.forkjoin.CarEntitySetProcessor;
import ru.otus.zaikin.drive2.forkjoin.TaskCarEntitySetPopulateDetails;
import ru.otus.zaikin.drive2.page.indexpage.IndexPagePO;
import ru.otus.zaikin.drive2.service.CarEntityService;
import ru.otus.zaikin.framework.DriverBase;
import ru.otus.zaikin.framework.utils.CSVUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class Application {

    public static void main(String[] args) throws IOException {
        log.debug("Application.main");
        new Application().start();
    }

    private void start() throws IOException {
        log.trace("start");
        DriverBase.instantiateDriverObject();
        AnnotationConfigApplicationContext appContext = ApplicationContextProvider.getInstance();
        appContext.scan("ru.otus.zaikin.drive2");
        appContext.refresh();
        log.debug("hibernate started");
        IndexPagePO indexPagePO = new IndexPagePO();
        indexPagePO.openSite();
        String brend = System.getProperty("car.brend", "Mazda");
        //read cars of brend
        indexPagePO.saveCarsOfBrend(brend);
        DriverBase.closeDriverObjects();
        //process all records in parallel
        List<CarEntitySet> list = new ArrayList<>();
        CarEntityService carEntityService = (CarEntityService) ApplicationContextProvider.getInstance().getBean("carEntityService");
        carEntityService.getRepository().findAll().forEach(list::add);
        CarEntitySetProcessor processor = new CarEntitySetProcessor(new TaskCarEntitySetPopulateDetails(list, 0, list.size()));
        processor.startProcessing();
        DriverBase.closeDriverObjects();
        //save to csv file
        List<CarEntitySet> listNew = new ArrayList<>();
        carEntityService.getRepository().findAll().forEach(listNew::add);
        writeToCsvFile(listNew, String.format("./log/%s.csv", brend));
        log.debug("processing completed!");
        ApplicationContextProvider.getInstance().close();
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
