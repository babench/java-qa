package ru.otus.zaikin.drive2.hibernate;

import lombok.extern.log4j.Log4j;
import org.hibernate.cfg.Configuration;
import ru.otus.zaikin.drive2.entity.CarBrendSet;
import ru.otus.zaikin.drive2.entity.CarEntitySet;
import ru.otus.zaikin.drive2.entity.DataSet;


@Log4j
final class HibernateConfigurationHelper {
    private HibernateConfigurationHelper() {
    }

    static Configuration getDefaultConfiguration(String url, String user, String password) {
        Configuration configuration = new Configuration();
        log.debug("Add classes");
        configuration.addAnnotatedClass(DataSet.class);
        configuration.addAnnotatedClass(CarBrendSet.class);
        configuration.addAnnotatedClass(CarEntitySet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", url);
        configuration.setProperty("hibernate.connection.username", user);
        configuration.setProperty("hibernate.connection.password", password);
        configuration.setProperty("hibernate.show_sql", "true");
//        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
//        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return configuration;
    }
}