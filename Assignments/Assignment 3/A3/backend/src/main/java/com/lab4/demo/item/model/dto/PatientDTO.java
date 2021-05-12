package com.lab4.demo.item.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;
    private String name;
    private Integer identityCardNumber;
    private Long cnp;
    private Date dateOfBirth;
    private String address;
}
