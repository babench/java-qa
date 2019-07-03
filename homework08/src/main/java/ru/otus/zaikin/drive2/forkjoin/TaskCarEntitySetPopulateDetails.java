package ru.otus.zaikin.drive2.forkjoin;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import ru.otus.zaikin.drive2.carpage.CarPagePO;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.framework.DriverBase;

import java.net.MalformedURLException;
import java.util.List;

import static ru.otus.zaikin.framework.DriverBase.getDriver;

/**
 * Process Car Entities from DB
 */
@Log4j2
class TaskCarEntitySetPopulateDetails {
    private final List<CarEntitySet> list;
    private final int start; /*start includes in compute*/
    private final int end; /*end not includes to compute*/
    final int size;
    private final HibernateDao dao;

    public HibernateDao getDao() {
        return dao;
    }

    TaskCarEntitySetPopulateDetails(List<CarEntitySet> list, int start, int end, HibernateDao dao) {
        System.out.println("TaskCarEntitySetPopulateDetails.TaskCarEntitySetPopulateDetails");
        this.list = list;
        this.start = start;
        this.end = end;
        this.size = end - start;
        this.dao = dao;
        log.debug("start:" + this.start);
        log.debug("end:" + this.end);
        log.debug("size:" + this.size);

    }

    TaskCarEntitySetPopulateDetails createSubTask(int subStart, int subEnd, HibernateDao dao) {
        return new TaskCarEntitySetPopulateDetails(list, start + subStart, start + subEnd, dao);
    }

    int compute() {
        //main compute here
        int objectsProcessed = 0;
        DriverBase.instantiateDriverObject();
        WebDriver driver = null;
        try {
            driver = getDriver();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert driver != null;
        CarPagePO carPagePO = new CarPagePO(dao);

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
        DriverBase.closeDriverObjects();
        return objectsProcessed;
    }
}