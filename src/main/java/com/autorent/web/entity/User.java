package com.autorent.web.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private String picUrl;
    private String driverLicence;
    private double driverHourlyRate;
    private String bankAccountNumber;

    private String token;
    private boolean active;
    private LocalDateTime tokenCreatedDate;

    @Enumerated(EnumType.STRING)
    private UserType userType;




}
