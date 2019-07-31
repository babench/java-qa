package ru.otus.zaikin.drive2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.zaikin.drive2.repository.CarEntityRepository;

@Service
public class CarEntityService {
    @Autowired
    CarEntityRepository repository;

    public CarEntityRepository getRepository() {
        return repository;
    }
}
