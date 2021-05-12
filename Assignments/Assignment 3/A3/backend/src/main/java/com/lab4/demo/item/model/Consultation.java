package com.lab4.demo.item.model;


import com.lab4.demo.user.model.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Column(length = 512)
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh")
    private Date dateOfConsult;

//    public Long getPatientId(){
//        return patient.getId();
//
//    }
//
//    public Long getDoctorId(){
//        return doctor.getId();
//    }
}
