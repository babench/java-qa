package ru.otus.zaikin;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;

@Log4j2
public class ApplicationHelper {
    static Session createHibernateSession(String url, String user, String password) {
        log.debug("Start Hibernate");
        HibernateFactorySessionHolder sessionHolder = new HibernateFactorySessionHolder(url, user, password);
        log.debug("Created executor");
        Session session = sessionHolder.openSession();
        log.debug(sessionHolder.getMetaData());
        return session;
    }
}
