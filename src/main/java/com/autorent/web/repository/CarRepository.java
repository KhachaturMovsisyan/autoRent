package com.autorent.web.repository;

import com.autorent.web.entity.Car;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {
    //List<Car> findTop3ByOrderByIdDesc();
}
