package com.autorent.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class MainController {




    @GetMapping("/")
    public String main() {

        return "index";
    }

    @GetMapping("/sign")
    public String login(@RequestParam(required = false) boolean error, ModelMap map) {
        map.addAttribute("error", error);
        return "login";
    }

    @GetMapping("/registration")
    public String register() {
        return "register";
    }

    @GetMapping("/registerAsDealer")
    public String registerDealer() {
        return "asDealer";
    }

    @GetMapping("/registerAsDriver")
    public String registerDriver() {
        return "asDriver";
    }




 }


