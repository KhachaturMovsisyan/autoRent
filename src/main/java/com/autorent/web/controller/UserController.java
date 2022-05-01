package com.autorent.web.controller;

import com.autorent.web.dto.CreateDealerRequest;
import com.autorent.web.dto.CreateDriverRequest;
import com.autorent.web.dto.CreateUserRequest;
import com.autorent.web.entity.User;
import com.autorent.web.entity.UserType;
import com.autorent.web.exception.UserNotFoundException;
import com.autorent.web.service.MailService;
import com.autorent.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final ModelMapper mapper;
    private final MailService mailService;



    @GetMapping("/profile")
    public String userProfile() {
        return "index";
    }



    @GetMapping("/user/activate")
    public String activateUser(ModelMap map, @RequestParam String token)  {
        User userFromDb = userService.findByToken(token).orElseThrow(UserNotFoundException::new);
        userService.activateUser(userFromDb);
        map.addAttribute("message", "User activated, please login!");
        return "activationPage";
    }




    @PostMapping("/register")
    public String register(@ModelAttribute @Valid CreateUserRequest createUserRequest,
                           BindingResult bindingResult,
                           @RequestParam("image") MultipartFile file,
                           ModelMap modelMap, Locale locale) throws IOException, MessagingException {

        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("errors", getVerifiedErrors(bindingResult));
            return "register";
        } else {
            if (userService.getUserByUserName(createUserRequest.getEmail()).isPresent()) {
                modelMap.addAttribute("existUser", "User with this email already exist");
                return "register";
            } else {
                User user = mapper.map(createUserRequest, User.class);
                userService.userSave(user, file);

                mailService.sendHtmlEmail(user.getEmail(),
                        "Welcome " + user.getSurname(),
                        user, " http://localhost:9090/user/activate?token=" + user.getToken(), "verifyEmail", locale);

                return "redirect:/";

            }

        }


    }


    @PostMapping("/driver/register")
    public String register(@ModelAttribute @Valid CreateDriverRequest createDriverRequest,
                           BindingResult bindingResult,
                           @RequestParam("image") MultipartFile file,
                           ModelMap modelMap, Locale locale) throws IOException, MessagingException {

        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("errors", getVerifiedErrors(bindingResult));
            return "asDriver";
        } else {
            if (userService.getUserByUserName(createDriverRequest.getEmail()).isPresent()) {
                modelMap.addAttribute("existUser", "User with this email already exist");
                return "asDriver";
            } else {
                User user = mapper.map(createDriverRequest, User.class);
                user.setUserType(UserType.DRIVER);
                userService.driverSave(user, file);
                mailService.sendHtmlEmail(user.getEmail(),
                        "Welcome " + user.getSurname(),
                        user, " http://localhost:9090/user/activate?token=" + user.getToken(), "verifyEmail", locale);

                return "redirect:/";

            }
        }
    }


    @PostMapping("/dealer/register")
    public String register(@ModelAttribute @Valid CreateDealerRequest createDealerRequest,
                           BindingResult bindingResult,
                           @RequestParam("image") MultipartFile file,
                           ModelMap modelMap, Locale locale) throws IOException, MessagingException {

        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("errors", getVerifiedErrors(bindingResult));
            return "asDealer";
        } else {
            if (userService.getUserByUserName(createDealerRequest.getEmail()).isPresent()) {
                modelMap.addAttribute("existUser", "User with this email already exist");
                return "asDealer";
            } else {
                User user = mapper.map(createDealerRequest, User.class);
                user.setUserType(UserType.DEALER);
                userService.dealerSave(user, file);

                mailService.sendHtmlEmail(user.getEmail(),
                        "Welcome " + user.getName(),
                        user, " http://localhost:9090/user/activate?token=" + user.getToken(), "verifyEmail", locale);

                return "redirect:/";
            }
        }
    }





    private List<String> getVerifiedErrors(BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        for (ObjectError allError : bindingResult.getAllErrors()) {
            errors.add(allError.getDefaultMessage());
        }
        return errors;
    }

}












