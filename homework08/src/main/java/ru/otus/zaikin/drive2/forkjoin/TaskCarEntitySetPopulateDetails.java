package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.page.carpage.CarPagePO;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.List;


/**
 * Process Car Entities from DB
 */
@Log4j2
public class TaskCarEntitySetPopulateDetails {
    private final List<CarEntitySet> list;
    private final int start; /*start includes in compute*/
    private final int end; /*end not includes to compute*/
    final int size;

    public TaskCarEntitySetPopulateDetails(List<CarEntitySet> list, int start, int end) {
        log.debug("TaskCarEntitySetPopulateDetails");
        this.list = list;
        this.start = start;
        this.end = end;
        this.size = end - start;
        log.debug("start:" + this.start);
        log.debug("end:" + this.end);
        log.debug("size:" + this.size);
    }

    TaskCarEntitySetPopulateDetails createSubTask(int subStart, int subEnd) {
        return new TaskCarEntitySetPopulateDetails(list, start + subStart, start + subEnd);
    }

    int compute() throws MalformedURLException {
        int objectsProcessed = 0;
        DriverBase.instantiateDriverObject();
        CarPagePO carPagePO = new CarPagePO();

        try {
            for (int i = start; i < end; i++)
                try {
                    carPagePO.openCarById(list.get(i).getId()).readFieldsAndUpdateEntity(list.get(i).getId());
                    objectsProcessed++;
                } catch (Exception e) {
                    log.error("Failed for " + list.get(i).toString());
                    log.error(e.getMessage());
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.debug("processed " + objectsProcessed);
        DriverBase.getDriver().quit();
        return objectsProcessed;
    }
}