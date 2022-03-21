package com.example.autorent.entity;


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
@Table(name = "massage")
public class Massage {



    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private Date date;
    private String massageText;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

}
