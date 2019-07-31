package ru.otus.zaikin.drive2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.zaikin.drive2.repository.CarBrendRepository;

@Service
public class CarBrendService {
    @Autowired
    CarBrendRepository repository;

    public CarBrendRepository getRepository() {
        return repository;
    }
}
