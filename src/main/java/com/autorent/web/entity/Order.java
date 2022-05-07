package com.autorent.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rent")
public class Order {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private String startDate;
    private String endDate;
    private double cost;


    @ManyToOne(fetch = FetchType.EAGER)
    private Car car;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private User driver;



    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
}
