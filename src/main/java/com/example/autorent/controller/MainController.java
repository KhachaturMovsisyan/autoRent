package com.example.autorent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

@GetMapping("/")
    public String main(){

        return "index";
    }

}
