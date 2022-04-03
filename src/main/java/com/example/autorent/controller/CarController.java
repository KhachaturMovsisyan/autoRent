package com.example.autorent.controller;

import com.example.autorent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class CarController {

    private final UserService userService;




    @GetMapping("/cars")
    public String main() {

        return "cars";
    }













}
