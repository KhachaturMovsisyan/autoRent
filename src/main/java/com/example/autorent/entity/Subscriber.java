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
@Table(name = "subscriber")
public class Subscriber {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private String email;



}
