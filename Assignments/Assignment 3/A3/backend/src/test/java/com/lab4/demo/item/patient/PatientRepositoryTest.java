package com.lab4.demo.item.patient;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.item.model.Patient;
import com.lab4.demo.item.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Patient patientSaved = repository.save(Patient.builder().name("whatever").build());

        assertNotNull(patientSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Patient.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Patient> patients = TestCreationFactory.listOf(Patient.class);
        repository.saveAll(patients);
        List<Patient> all = repository.findAll();
        assertEquals(patients.size(), all.size());
    }
}