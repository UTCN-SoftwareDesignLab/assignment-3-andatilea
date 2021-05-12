package com.lab4.demo.socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CheckInDTO {
    private Long patientId;
    private Long doctorId;
    private Date dateOfConsult;
}
