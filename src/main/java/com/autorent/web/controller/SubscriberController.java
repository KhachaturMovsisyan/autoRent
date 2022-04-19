package com.autorent.web.controller;

import com.autorent.web.entity.Subscriber;
import com.autorent.web.dto.CreateSubscriberRequest;
import com.autorent.web.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final ModelMapper mapper;


    @PostMapping("/subscribe")
    public String subscribe(@ModelAttribute @Valid CreateSubscriberRequest createSubscriberRequest,
                            BindingResult bindingResult, ModelMap modelmap) {

        if (bindingResult.hasErrors()) {

            modelmap.addAttribute("errors", getVerifiedErrors(bindingResult));
            return "index";
        } else {
            if (subscriberService.getSubscriberByEmail(createSubscriberRequest.getEmail()).isPresent()) {
                modelmap.addAttribute("existSubscriber", "Subscriber with this email already exist");
                return "index";
            } else {
                Subscriber subscriber = mapper.map(createSubscriberRequest, Subscriber.class);
                subscriberService.save(subscriber);
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