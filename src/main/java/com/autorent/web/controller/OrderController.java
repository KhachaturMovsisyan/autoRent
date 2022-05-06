package com.autorent.web.controller;

import com.autorent.web.dto.CreateOrderRequest;
import com.autorent.web.entity.Car;
import com.autorent.web.entity.Order;
import com.autorent.web.security.CurrentUser;
import com.autorent.web.service.CarService;
import com.autorent.web.service.OrderService;
import com.autorent.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class OrderController {


    private final ModelMapper mapper;
    private final CarService carService;
    private final OrderService orderService;

    private final UserService userService;


    @GetMapping("/order/{id}")
    public String addOrder(@PathVariable int id, ModelMap map) {
        map.addAttribute("car", carService.findById(id));
        map.addAttribute("drivers", userService.drivers());
        return "ordering";
    }



    @PostMapping("/save/order")
    public String saveOrder(@ModelAttribute @Valid CreateOrderRequest createOrderRequest,
                            @AuthenticationPrincipal CurrentUser currentUser) throws IOException {

        Order order = mapper.map(createOrderRequest, Order.class);
        orderService.saveOrder(currentUser, order);

        return "redirect:/";
    }

//    @GetMapping("/cars/{id}")
//    public String singleItem(@PathVariable int id, ModelMap map) {
//        map.addAttribute("car", carService.findById(id));
//        return "car-detail";
//    }


}












