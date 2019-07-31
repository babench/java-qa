package ru.otus.zaikin.drive2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.zaikin.drive2.entity.CarEntitySet;

@Repository
public interface CarEntityRepository extends CrudRepository<CarEntitySet, Long> {
}
