package com.example.autorent.controller;

import com.example.autorent.dto.CreateUserRequest;
import com.example.autorent.entity.User;
import com.example.autorent.entity.UserType;
import com.example.autorent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {

    private final ModelMapper mapper;
    private final UserService userService;


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

    @GetMapping("/asDealer")
    public String registerDealer() {
        return "asDealer";
    }

    @GetMapping("/asDriver")
    public String registerDriver() {
        return "asDriver";
    }

//    @GetMapping("/sign?error")
//    public String loginFailure(ModelMap map) {
//        String error = "Wrong username or password";
//        map.addAttribute("error", error);
//        return "login";
//    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid CreateUserRequest createUserRequest,
                           BindingResult bindingResult,
                           @RequestParam("image") MultipartFile file,
                           ModelMap modelMap) throws IOException {
//
        if (bindingResult.hasErrors()) {
            return "redirect:/registration";
        } else {
            User user = mapper.map(createUserRequest, User.class);
            user.setUserType(UserType.USER);
            userService.save(user, file);
            return "redirect:/";

        }

    }
}

