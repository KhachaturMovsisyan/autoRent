package com.autorent.web.controller;


import com.autorent.web.security.CurrentUser;
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
