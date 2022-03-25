package com.example.autorent.controller;

import com.example.autorent.entity.User;
import com.example.autorent.entity.UserType;
import com.example.autorent.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //  private final MailService mailService;


    @GetMapping("/user/registration")
    public String register() {
        return "register";
    }

    @PostMapping("/user/registration")
    public String addUser(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {

        user.setUserType(UserType.USER);
        userService.save(user, file);
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "index";
    }
//    @PostMapping("/errorPage")
//    public String error() {
//        return "404";
//    }


//    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
//    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
//
//
//        InputStream inputStream = Files.newInputStream(Paths.get(imagePath, picName));
//
//        return IOUtils.toByteArray(inputStream);
//    }


}

//    @PostMapping("/user/add")
//    public String addUser(@ModelAttribute User user, ModelMap map) {
//        List<String> errorMsgs = new ArrayList<>();
//        if (user.getName() == null || user.getName().equals("")) {
//            errorMsgs.add("name is required");
//        }
//        if (user.getSurname() == null || user.getSurname().equals("")) {
//            errorMsgs.add("surname is required");
//        }
//        if (user.getEmail() == null || user.getEmail().equals("")) {
//            errorMsgs.add("email is required");
//        }
//        if (!errorMsgs.isEmpty()) {
//            map.addAttribute("errors", errorMsgs);
//            return "saveUser";
//        }
//
////        userService.save(user);
////        mailService.sendMail(user.getEmail(), "Welcome " + user.getSurname(), "You have successfully register, " + user.getName());
////        return "redirect:/";
//    }

//    @GetMapping("/editUser/{id}")
//    public String editUserPage(ModelMap map,
//                               @PathVariable("id") int id) {
//        map.addAttribute("user", userService.findById(id));
//        return "saveUser";
//
//    }
