package ru.otus.zaikin.drive2.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.otus.zaikin.drive2.base.Dao;
import ru.otus.zaikin.drive2.entity.DataSet;
import ru.otus.zaikin.drive2.exception.RecordNotExistsException;

import java.util.List;
import java.util.function.Supplier;

public class HibernateDao implements Dao<DataSet, Long> {
    private static final String EMPTY_ENTITY_IS_NOT_ALLOWED = "Empty entity is not allowed";
    private Session session;

    public HibernateDao(Session session) {
        this.session = session;
    }

    @Override
    public void create(DataSet entity) {
        if (entity == null) throw new IllegalArgumentException(EMPTY_ENTITY_IS_NOT_ALLOWED);
        runInTransaction(() -> (Long) session.save(entity));
    }

    @Override
    public void update(DataSet entity) {
        if (entity == null) throw new IllegalArgumentException(EMPTY_ENTITY_IS_NOT_ALLOWED);
        runInTransaction(() -> {
            session.update(entity);
            return null;
        });
    }

    @Override
    public void delete(DataSet entity) {
        if (entity == null) throw new IllegalArgumentException(EMPTY_ENTITY_IS_NOT_ALLOWED);
        runInTransaction(() -> {
            session.delete(entity);
            return null;
        });
    }

    @Override
    public <D extends DataSet> D get(Long id, Class<D> clazz) {
        if (id == null) throw new IllegalArgumentException("Empty id is not allowed");
        if (clazz == null) throw new IllegalArgumentException("Empty clazz is not allowed");

        DataSet dataSet = session.get(clazz, id);
        if (dataSet == null) throw new RecordNotExistsException("{" + clazz.getSimpleName() + ":" + id + "}");
        return (D) dataSet;
    }

    @Override
    public <D extends DataSet> List<D> getAll(Class<D> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Empty clazz is not allowed");
        return session.createQuery("select t from " + clazz.getSimpleName() + " t").getResultList();
    }

    private <R> R runInTransaction(Supplier<R> supplier) {
        Transaction transaction = session.beginTransaction();
        R result = supplier.get();
        transaction.commit();
        return result;
    }
}