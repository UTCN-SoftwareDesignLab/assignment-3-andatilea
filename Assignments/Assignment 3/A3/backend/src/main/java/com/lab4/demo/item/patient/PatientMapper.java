package com.lab4.demo.item.patient;

import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.model.dto.PatientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientDTO toDto(Patient patient);


    Patient fromDto(PatientDTO patient);

}
