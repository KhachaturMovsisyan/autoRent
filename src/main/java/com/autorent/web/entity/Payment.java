package com.autorent.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private Date date;
    private double cost;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;


}
