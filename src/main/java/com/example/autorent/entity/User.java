package com.example.autorent.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private UserType userType;


}