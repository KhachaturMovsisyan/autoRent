package com.autorent.web.controller;

import com.autorent.web.dto.CreateCarRequest;
import com.autorent.web.entity.Car;
import com.autorent.web.security.CurrentUser;
import com.autorent.web.service.CarService;
import com.autorent.web.service.PicturesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class CarController {



    private final ModelMapper mapper;
    private final CarService carService;
    private final PicturesService picturesService;

    @GetMapping("/addCar")
    public String addCar() {
        return "addCar";
    }

    @GetMapping("/cars")
    public String showCars(ModelMap map) {
        map.addAttribute("pictures", picturesService.findAll());
        map.addAttribute("cars", carService.findAll());
        return "cars";
    }


    @PostMapping("/saveCar")
    public String register(@ModelAttribute @Valid CreateCarRequest createCarRequest, @RequestParam("images") MultipartFile[] file,
                           @AuthenticationPrincipal CurrentUser currentUser) throws IOException, MessagingException {

        Car car = mapper.map(createCarRequest, Car.class);
        carService.saveCar(currentUser, car, file);

        return "redirect:/";
    }


}












