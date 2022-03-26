package com.example.autorent.controller;

import com.example.autorent.entity.User;
import com.example.autorent.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    @ModelAttribute(name = "currentUser")
    public CurrentUser currentUser(@AuthenticationPrincipal CurrentUser currentUser){
        return currentUser;
    }
}
