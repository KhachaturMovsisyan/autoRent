package com.autorent.web.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequiredArgsConstructor
public class MainController {


    @Value("${image.upload.path}")
    private String imagePath;

    @GetMapping("/")
    public String main() {

        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "redirect:" + "https://vk.com/wall-111905078_11972";
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

    @GetMapping(value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }


 }


