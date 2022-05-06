package com.autorent.web.service;

import com.autorent.web.entity.Car;
import com.autorent.web.entity.Pictures;
import com.autorent.web.entity.StatusType;
import com.autorent.web.repository.CarRepository;
import com.autorent.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final PicturesService picturesService;

    @Value("${image.upload.path}")
    private String imagePath;


    public Car saveCar(CurrentUser currentUser, Car car, MultipartFile[] file) throws IOException {
        car.setStatusType(StatusType.FREE);
        car.setUser(currentUser.getUser());
        Car savedCar = carRepository.save(car);
        saveCarImages(file, car);
        return savedCar;
    }


    public List<Car> findAll() {
        return carRepository.findAll();
    }



    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

//    public List<Car> findLast10(){
//
//        return carRepository.findTop3ByOrderByIdDesc();
//
//    }


    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    private void saveCarImages(MultipartFile[] uploadedFiles, Car car) throws IOException {
        if (uploadedFiles.length != 0) {
            for (MultipartFile uploadedFile : uploadedFiles) {
                if (!uploadedFile.isEmpty()) {
                    String fileName = System.nanoTime() + "_" + uploadedFile.getOriginalFilename();
                    File newFile = new File(imagePath + fileName);
                    uploadedFile.transferTo(newFile);
                    Pictures pictures = Pictures.builder()
                            .picUrl(fileName)
                            .car(car)
                            .build();

                    picturesService.savePics(pictures);
                }
            }
        }
    }
}

