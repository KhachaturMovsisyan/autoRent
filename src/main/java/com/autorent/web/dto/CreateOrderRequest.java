package com.autorent.web.dto;

import com.autorent.web.entity.Car;
import com.autorent.web.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

    private Date startDate;
    private Date endDate;

    @ManyToOne
    private Car car;

    @ManyToOne
    private User user;
    @ManyToOne
    private User driver;

}
