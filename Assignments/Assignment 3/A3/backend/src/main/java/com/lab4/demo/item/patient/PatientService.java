package com.lab4.demo.item.patient;

import com.lab4.demo.item.model.Consultation;
import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.model.dto.PatientDTO;
import com.lab4.demo.item.patient.PatientMapper;
import com.lab4.demo.item.patient.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found: " + id));
    }

    public List<PatientDTO> findAll() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO create(PatientDTO patient) {
        return patientMapper.toDto(patientRepository.save(
                patientMapper.fromDto(patient)
        ));
    }

    public PatientDTO update(PatientDTO patient) {
        Patient actPatient = findById(patient.getId());
        actPatient.setName(patient.getName());
        actPatient.setIdentityCardNumber(patient.getIdentityCardNumber());
        actPatient.setCnp(patient.getCnp());
        actPatient.setAddress(patient.getAddress());
        actPatient.setDateOfBirth(patient.getDateOfBirth());
        return patientMapper.toDto(
                patientRepository.save(actPatient)
        );
    }

    public PatientDTO changeName(Long id, String newName) {
        Patient patient = findById(id);
        patient.setName(newName);
        return patientMapper.toDto(
                patientRepository.save(patient)
        );
    }

    public PatientDTO get(Long id) {
        return patientMapper.toDto(findById(id));
    }

    public void delete(Long id) {
        Patient actPatient = findById(id);
        patientRepository.delete(actPatient);
        //System.out.println("Patient deletion successful!");
    }
}
