package com.autorent.web.dto;


import com.autorent.web.entity.CategoryType;
import com.autorent.web.entity.StatusType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    private String model;
    @NotBlank(message = "description is empty")
    private String description;
    @NotNull
    private double pricePerDay;
    private String carModelYear;
    private String Mark;


    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
    @Enumerated(EnumType.STRING)
    private StatusType statusType;

   private String type;


}
