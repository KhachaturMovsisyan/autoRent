package com.autorent.web.service;

import com.autorent.web.entity.Order;
import com.autorent.web.entity.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarSchedulingService {

    private final OrderService orderService;

    @Scheduled(fixedRate=1000)
    public void setFreeOfCar(){
        List<Order> orders = orderService.findAll();

        for (Order order : orders) {
            LocalDate endLocalDate = LocalDate.parse(order.getEndDate());
            if(endLocalDate.isAfter(LocalDate.now())){
                order.getCar().setStatusType(StatusType.FREE);
            }
        }

    }














}
