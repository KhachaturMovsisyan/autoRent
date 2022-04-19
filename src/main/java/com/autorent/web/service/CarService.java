package com.autorent.web.service;

import com.autorent.web.entity.Car;
import com.autorent.web.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarService {

private final CarRepository carRepository;


    public Car carSave(Car car) {

        return carRepository.save(car);
    }



}

