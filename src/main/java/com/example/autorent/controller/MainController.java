package com.example.autorent.controller;

import com.example.autorent.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String main(ModelMap modelMap,
                       @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("userol", currentUser.getUser());
        return "index";
    }

    @GetMapping("/sign")
    public String login() {
        return "login";
    }

}
