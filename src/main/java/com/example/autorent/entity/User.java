package com.example.autorent.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "Name is required")
    private String name;
    private String surname;
    @Email
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
