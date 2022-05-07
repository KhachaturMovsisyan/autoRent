package com.autorent.web.controller;

import com.autorent.web.dto.CreateCarRequest;
import com.autorent.web.entity.Car;
import com.autorent.web.security.CurrentUser;
import com.autorent.web.service.CarService;
import com.autorent.web.service.PicturesService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class CarController {


    private final ModelMapper mapper;
    private final CarService carService;
    private final PicturesService picturesService;


    @GetMapping("/cars")
    public String showCars(ModelMap map,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "6") int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Car> carPage = carService.findAll(pageRequest);
        map.addAttribute("carPage", carPage);

        int totalPages = carPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);
        }

        map.addAttribute("pictures", picturesService.findAll());
        return "cars";
    }

    @GetMapping("/addCar")
    public String addCar() {
        return "addCar";
    }


    @PostMapping("/saveCar")
    public String register(@ModelAttribute @Valid CreateCarRequest createCarRequest, @RequestParam("images") MultipartFile[] file,
                           @AuthenticationPrincipal CurrentUser currentUser) throws IOException, MessagingException {

        Car car = mapper.map(createCarRequest, Car.class);
        carService.saveCar(currentUser, car, file);
        return "redirect:/";
    }

    @GetMapping("/cars/{id}")
    public String singleItem(@PathVariable int id, ModelMap map) {
        if (carService.findById(id).isEmpty()) {
            return "404";
        }
        map.addAttribute("car", carService.findById(id));
        return "car-detail";
    }
}












