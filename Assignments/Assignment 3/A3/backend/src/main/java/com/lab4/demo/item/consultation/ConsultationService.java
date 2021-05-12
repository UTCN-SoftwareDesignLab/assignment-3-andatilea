package com.lab4.demo.item.consultation;

import com.lab4.demo.item.model.Consultation;
import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.model.dto.ConsultationDTO;
import com.lab4.demo.item.patient.PatientService;
import com.lab4.demo.user.UserService;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    private final UserService userService;
    private final PatientService patientService;

    private Consultation findById(Long id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + id));
    }


    public List<ConsultationDTO> findAll() {
        return consultationRepository.findAll().stream()
                .map(consultationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ConsultationDTO create(ConsultationDTO consultation) {

        Consultation actConsultation = consultationRepository
                .findByDoctorIdAndDateOfConsult(consultation.getDoctorId(),
                        consultation.getDateOfConsult());

        if(actConsultation == null){
            Patient patient = patientService.findById(consultation.getPatientId());
            User doctor = userService.findById(consultation.getDoctorId());

            return (consultationMapper.toDto(consultationRepository.save(Consultation.builder()
                    .id(consultation.getId())
                    .doctor(doctor)
                    .patient(patient)
                    .dateOfConsult(consultation.getDateOfConsult())
                    .description(consultation.getDescription())
                    .build())));
      }
        else{
            //System.out.println("another consultation is scheduled then");
            return null;
        }
    }

    public ConsultationDTO update(ConsultationDTO consultation) {
        Consultation actConsultation = findById(consultation.getId());
        actConsultation.setDescription(consultation.getDescription());
        actConsultation.setDateOfConsult(consultation.getDateOfConsult());
        return consultationMapper.toDto(consultationRepository.save(actConsultation));
    }

    public void delete(Long id) {
        Consultation actConsultation = findById(id);
        consultationRepository.delete(actConsultation);
       // System.out.println("Consultation deletion successful!");
    }

    public ConsultationDTO get(Long id) {
        return consultationMapper.toDto(findById(id));
    }

    public ConsultationDTO updateDescription(ConsultationDTO consultation){

        Consultation pastConsultation = findById(consultation.getId());
        String description = pastConsultation.getDescription();
        String toUpdate = consultation.getDescription();
        pastConsultation.setDescription(toUpdate);
        return consultationMapper.toDto(
                consultationRepository.save(pastConsultation)
        );

    }
}
