package ru.otus.zaikin;


import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;

import static ru.otus.zaikin.ApplicationHelper.createHibernateSession;

@Log4j2
public class Application {

    private Session session;

    public Session getSession() {
        return session;
    }

    public static void main(String[] args) {
        log.debug("Application.main");
        new Application().start();
    }

    public void start() {
        session = createHibernateSession(
                System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", ""));
        log.debug("hibernate started");
    }
}
