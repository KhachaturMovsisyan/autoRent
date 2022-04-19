package com.autorent.web.controller;

import com.autorent.web.dto.CreateCarRequest;
import com.autorent.web.entity.Car;
import com.autorent.web.entity.Pictures;
import com.autorent.web.entity.StatusType;
import com.autorent.web.security.CurrentUser;
import com.autorent.web.service.CarService;
import com.autorent.web.service.PicturesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CarController {


    @Value("${image.upload.path}")
    private String imagePath;

    private final ModelMapper mapper;
    private final PicturesService picturesService;
    private final CarService carService;

    @GetMapping("/addCar")
    public String addCar() {
        return "addCar";
    }


    @PostMapping("/saveCar")
    public String register(@ModelAttribute @Valid CreateCarRequest createCarRequest, @RequestParam("images") MultipartFile[] file,
                            @AuthenticationPrincipal CurrentUser currentUser) throws IOException, MessagingException {

        Car car = mapper.map(createCarRequest, Car.class);
        car.setStatusType(StatusType.FREE);
        car.setUser(currentUser.getUser());
        carService.carSave(car);
        saveCarImages(file,car);

        return "redirect:/";


    }



    private void saveCarImages(MultipartFile[] uploadedFiles, Car car) throws IOException {
        if (uploadedFiles.length != 0) {
            for (MultipartFile uploadedFile : uploadedFiles) {
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












