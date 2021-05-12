package com.lab4.demo.item.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.model.dto.PatientDTO;
import com.lab4.demo.item.patient.PatientMapper;
import com.lab4.demo.item.patient.PatientRepository;
import com.lab4.demo.item.patient.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.TestCreationFactory.randomDate;
import static org.mockito.Mockito.when;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> all = patientService.findAll();

        Assertions.assertEquals(patients.size(), all.size());
    }

    @Test
    void create() {
        PatientDTO patientDTO = (PatientDTO) TestCreationFactory.listOf(PatientDTO.class).get(0);

        Patient patient = Patient.builder()
                .id(patientDTO.getId())
                .name(patientDTO.getName())
                .identityCardNumber(patientDTO.getIdentityCardNumber())
                .address(patientDTO.getAddress())
                .cnp(patientDTO.getCnp())
                .dateOfBirth(patientDTO.getDateOfBirth())
                .build();

        when(patientMapper.fromDto(patientDTO)).thenReturn(patient);
        when(patientMapper.toDto(patient)).thenReturn(patientDTO);
        when(patientRepository.save(patient)).thenReturn(patient);

        Assertions.assertEquals(patientService.create(patientDTO), patientDTO);
    }

    @Test
    void update(){
        Patient patient1 = Patient.builder()
                .name("Test")
                .identityCardNumber(randomInt())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .address(randomString())
                .build();


        PatientDTO patient2 = PatientDTO.builder()
                .name(randomString())
                .identityCardNumber(randomInt())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .address(randomString())
                .build();

        when(patientRepository.findById(patient1.getId())).thenReturn(Optional.of(patient1));
        patient2.setName("test");
        patientService.update(patient2);
        Assertions.assertEquals("test", patient2.getName());
    }

    @Test
    void delete() {

        Patient patient = Patient.builder()
                .name(randomString())
                .identityCardNumber(randomInt())
                .cnp(randomLong())
                .dateOfBirth(randomDate())
                .address(randomString())
                .build();

        when(patientRepository.save(patient)).thenReturn(patient);

        patientRepository.delete(patient);

        List<PatientDTO> all = patientService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }

    @Test
    void deleteAll() {
        patientRepository.deleteAll();
        List<PatientDTO> all = patientService.findAll();
        Assertions.assertEquals(all.size(), 0);
    }
}