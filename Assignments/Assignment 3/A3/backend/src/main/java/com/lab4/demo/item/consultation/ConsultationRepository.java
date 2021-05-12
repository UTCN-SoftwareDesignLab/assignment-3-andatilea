package com.lab4.demo.item.consultation;

import com.lab4.demo.item.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Consultation findByDoctorIdAndDateOfConsult(Long doctorId, Date dateOfConsult);


}
