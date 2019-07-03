package ru.otus.zaikin.drive2.base;

import java.util.List;

public interface Dao<T, K> {
    void create(T entity);

    void update(T entity);

    void delete(T entity);

    <D extends T> D get(K id, Class<D> clazz);

    <D extends T> List<D> getAll(Class<D> clazz);
}