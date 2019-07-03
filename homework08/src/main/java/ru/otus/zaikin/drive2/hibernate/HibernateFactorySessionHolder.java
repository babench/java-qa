package ru.otus.zaikin.drive2.hibernate;

import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Log4j
public final class HibernateFactorySessionHolder {

    private SessionFactory sessionFactory;

    public HibernateFactorySessionHolder(String url, String user, String password) {
        createNewSessionFactory(url, user, password);
    }

    private void createNewSessionFactory(String url, String user, String password) {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration configuration = HibernateConfigurationHelper.getDefaultConfiguration(url, user, password);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }


    public void shutdown() {
        sessionFactory.close();
    }

    public DatabaseMetaData getMetaData() {
        DatabaseMetaData databaseMetaData = null;
        Connection connection = ((SessionImpl) openSession()).connection();
        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            log.error(e);
        }
        return databaseMetaData;
    }
}