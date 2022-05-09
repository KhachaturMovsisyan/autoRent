package com.autorent.web.controller;

import com.autorent.web.dto.CreateOrderRequest;
import com.autorent.web.entity.*;
import com.autorent.web.security.CurrentUser;
import com.autorent.web.service.CarService;
import com.autorent.web.service.MailService;
import com.autorent.web.service.OrderService;
import com.autorent.web.service.UserService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

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


    @PostMapping("/order/save")
    public String saveOrder(@ModelAttribute @Valid CreateOrderRequest createOrderRequest,
                            @AuthenticationPrincipal CurrentUser currentUser, @RequestParam("carId") int id, ModelMap map) {

        LocalDate startDate = createOrderRequest.getStartDate();
        LocalDate endDate = createOrderRequest.getEndDate();
        StringBuilder message = new StringBuilder("");
        Car orderedCar = carService.findById(id).get();
        Order order = mapper.map(createOrderRequest, Order.class);
        if (startDate.isAfter(endDate)) {
            message.append("Wrong dates!!!");
            map.addAttribute("message", message);
            return "redirect:/order/" + id;
        }
        if (createOrderRequest.getDriver() == null) {

            orderService.saveOrderWithoutDriver( order, orderedCar,createOrderRequest.getPaymentType(), currentUser, startDate, endDate);
            map.addAttribute("message", message.append("Your order is completed"));

            return "redirect:/order/" + id;
        }

        orderService.saveOrderWithDriver(order,orderedCar,createOrderRequest.getPaymentType(),currentUser,createOrderRequest.getDriver(), startDate,endDate);
        map.addAttribute("message", message.append("Your order is completed"));
        return "redirect:/order/" + id;
    }


}












