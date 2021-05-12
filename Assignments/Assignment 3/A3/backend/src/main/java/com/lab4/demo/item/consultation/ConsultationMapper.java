package com.lab4.demo.item.consultation;

import com.lab4.demo.item.model.Consultation;
import com.lab4.demo.item.model.dto.ConsultationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    ConsultationDTO toDto(Consultation consultation);


    Consultation fromDto(ConsultationDTO consultation);

}
