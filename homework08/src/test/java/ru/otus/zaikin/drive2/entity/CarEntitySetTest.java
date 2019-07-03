package ru.otus.zaikin.drive2.entity;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.otus.zaikin.drive2.hibernate.HibernateDao;
import ru.otus.zaikin.drive2.hibernate.HibernateFactorySessionHolder;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class CarEntitySetTest {
    private HibernateDao dao;
    private HibernateFactorySessionHolder sessionHolder;

    @BeforeMethod
    public void setUp() {
        sessionHolder = new HibernateFactorySessionHolder(
                System.getProperty("DB_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"),
                System.getProperty("DB_USER", "SA"),
                System.getProperty("DB_PASSWORD", ""));
        Session session = sessionHolder.openSession();
        dao = new HibernateDao(session);

    }

    @Test
    public void shouldStartWithHibernateSession() {
        assertThat(dao).isNotNull();
    }

    @Test
    public void shouldSaveBrend() {
        CarBrendSet entity = new CarBrendSet();
        entity.setName("Audi");
        dao.create(entity);
        CarBrendSet carBrendSetRead = dao.get(1L, CarBrendSet.class);
        assertThat(carBrendSetRead).isNotNull();
        assertThat(carBrendSetRead.getName()).isEqualTo(entity.getName());
        log.debug(carBrendSetRead);
    }
}