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

    private final MailService mailService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


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

            order.setStartDate(startDate.format(formatter));
            order.setEndDate(endDate.format(formatter));
            order.setCost(Period.between(startDate, endDate).getDays() * orderedCar.getPricePerDay());
            order.setUser(currentUser.getUser());
            order.setPaymentType(createOrderRequest.getPaymentType());
            order.setCar(orderedCar);
            orderedCar.setStatusType(StatusType.BUSY);
            map.addAttribute("message", message.append("Your order is completed"));
            orderService.saveOrder(order);
            messageToMails(order, currentUser.getUser());
            return "redirect:/order/" + id;
        }

        order.setStartDate(startDate.format(formatter));
        order.setEndDate(endDate.format(formatter));
        order.setDriver(createOrderRequest.getDriver());
        order.setCost(Period.between(startDate, endDate).getDays() * (orderedCar.getPricePerDay() + createOrderRequest.getDriver().getDriverHourlyRate()));
        order.setUser(currentUser.getUser());
        order.setPaymentType(createOrderRequest.getPaymentType());
        order.setCar(orderedCar);
        orderedCar.setStatusType(StatusType.BUSY);
        carService.findById(id).get().setStatusType(StatusType.BUSY);
        userService.setBusy(createOrderRequest.getDriver());
        messageToMails(order, currentUser.getUser());
        messageToMails(order, currentUser.getUser(), order.getDriver());

        map.addAttribute("message", message.append("Your order is completed"));
        orderService.saveOrder(order);
        return "redirect:/order/" + id;
    }

    private void messageToMails(Order order, User currentUser) {
        String messageToOwner = "Your car " + order.getCar().getModel() +
                " " + order.getCar().getMark() + " The price of order is " + order.getCost()
                + " from " + order.getStartDate() + " to " + order.getEndDate() + " ."
                + " If you want to contact with client, this is client's contacts " + currentUser.getEmail()
                + " phone " + currentUser.getPhoneNumber() + " Enjoy! Best Regards";

        String messageToOrder = "You just ordered" + order.getCar().getModel() +
                " " + order.getCar().getMark() + " The price of order is " + order.getCost()
                + " from " + order.getStartDate() + " to " + order.getEndDate() + " ."
                + " If you want to contact with owner, this is owner's contacts " + order.getCar().getUser().getEmail()
                + " phone " + order.getCar().getUser().getPhoneNumber() + " Enjoy! Best Regards";

        mailService.sendMail(currentUser.getEmail(), "Your Order", messageToOrder);
        mailService.sendMail(order.getUser().getEmail(), "New Order", messageToOwner);
    }

    private void messageToMails(Order order, User currentUser, User driver) {
        String messageToDriver = "You just invented to drive" + order.getCar().getModel() +
                " " + order.getCar().getMark() + " The pay of  this work is " + order.getCost()
                + " from " + order.getStartDate() + " to " + order.getEndDate() + " ."
                + " If you want to contact with owner or clients, this is owner's and clients contacts the owner` " + order.getCar().getUser().getEmail()
                + " phone " + order.getCar().getUser().getPhoneNumber() + " and this is clients contacts` " + currentUser.getEmail()
                + " phone " + currentUser.getPhoneNumber() + " Enjoy! Best Regards";
        mailService.sendMail(driver.getEmail(), "New Invention", messageToDriver);
    }
}












