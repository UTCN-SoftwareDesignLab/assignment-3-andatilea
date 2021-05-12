//package com.lab4.demo.item.model.dto;
//
//
//import com.lab4.demo.item.model.Patient;
//import com.lab4.demo.user.model.User;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//
//public class ConsultationDTO {
//
//    private Long id;
//
//    private User doctor;
//
//    private Patient patient;
//
//    private String description;
//
//    private Date dateOfConsult;
//
//}

package com.lab4.demo.item.model.dto;

import com.lab4.demo.item.model.Patient;
import com.lab4.demo.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {

    private Long id;
    private Long patientId;
    public Long doctorId;
    private String description;
    private Date dateOfConsult;
}

