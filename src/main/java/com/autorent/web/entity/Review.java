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
@Table(name = "review")
public class Review {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int Id;
    private Date date;
    private String massageText;

    @ManyToOne(fetch = FetchType.EAGER)
    private User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private User toUser;
}
