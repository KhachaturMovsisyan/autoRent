package com.example.autorent.controller;

import com.example.autorent.dto.CreateUserRequest;
import com.example.autorent.entity.User;
import com.example.autorent.entity.UserType;
import com.example.autorent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    @Value("${image.upload.path}")
    private String imagePath;

    private final UserService userService;
    private final ModelMapper mapper;
    //  private final MailService mailService;


    @GetMapping("/profile")
    public String userProfile() {
        return "index";
    }

    @PostMapping("/user/registration")
    public String addUser(@RequestParam("image") MultipartFile file,
                          @ModelAttribute @Valid CreateUserRequest createUserRequest ) throws IOException {

        User user = mapper.map(createUserRequest, User.class);
        user.setUserType(UserType.USER);
        userService.save(user, file);
        return "redirect:/";

    }


    @PostMapping("/driver/registration")
    public String addDriver(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {

        user.setUserType(UserType.DRIVER);
        userService.save(user, file);
        return "redirect:/";
    }

    @PostMapping("/dealer/registration")
    public String addDealer(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {

        user.setUserType(UserType.DEALER);
        userService.save(user, file);
        return "redirect:/";
    }


    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }


}







