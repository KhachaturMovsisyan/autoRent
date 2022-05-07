package com.autorent.web.dto;

import com.autorent.web.entity.Car;
import com.autorent.web.entity.PaymentType;
import com.autorent.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    private PaymentType paymentType;
    private User driver;





}
