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
@Table(name = "order")
public class Order {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User user;
    @ManyToOne
    private User driver;
}
