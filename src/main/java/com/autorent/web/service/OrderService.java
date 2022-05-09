package com.autorent.web.service;

import com.autorent.web.entity.*;
import com.autorent.web.repository.OrderRepository;
import com.autorent.web.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final MailService mailService;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void saveOrderWithoutDriver(Order order, Car orderedCar, PaymentType paymentType, CurrentUser currentUser,
                                       LocalDate startDate, LocalDate endDate) {

        setStartAndEndAndCalculateCost(startDate,endDate,orderedCar,order);
        order.setUser(currentUser.getUser());
        order.setPaymentType(paymentType);
        order.setCar(orderedCar);
        orderedCar.setStatusType(StatusType.BUSY);
        messageToMails(order,currentUser.getUser());
        orderRepository.save(order);
    }


    public void saveOrderWithDriver(Order order, Car orderedCar, PaymentType paymentType, CurrentUser currentUser, User driver,
                                       LocalDate startDate, LocalDate endDate) {

        setStartAndEndAndCalculateCost(startDate,endDate,orderedCar,order);
        order.setUser(currentUser.getUser());
        order.setPaymentType(paymentType);
        order.setCar(orderedCar);
        orderedCar.setStatusType(StatusType.BUSY);
        messageToMails(order,currentUser.getUser());
        userService.setBusy(driver);
        messageToMails(order,currentUser.getUser());
        messageToDriver(order,currentUser.getUser(),driver);

        orderRepository.save(order);
    }






    private void setStartAndEndAndCalculateCost(LocalDate startDate, LocalDate endDate, Car orderedCar, Order order) {
        order.setStartDate(startDate.format(formatter));
        order.setEndDate(endDate.format(formatter));
        order.setCost(Period.between(startDate, endDate).getDays() * orderedCar.getPricePerDay());
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

    private void messageToDriver(Order order, User currentUser, User driver) {
        String messageToDriver = "You just invented to drive" + order.getCar().getModel() +
                " " + order.getCar().getMark() + " The pay of  this work is " + order.getCost()
                + " from " + order.getStartDate() + " to " + order.getEndDate() + " ."
                + " If you want to contact with owner or clients, this is owner's and clients contacts the owner` "
                + order.getCar().getUser().getEmail()
                + " phone " + order.getCar().getUser().getPhoneNumber()
                + " and this is clients contacts` " + currentUser.getEmail()
                + " phone " + currentUser.getPhoneNumber() + " Enjoy! Best Regards";
        mailService.sendMail(driver.getEmail(), "New Invention", messageToDriver);
    }
}
