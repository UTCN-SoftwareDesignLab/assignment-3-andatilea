package com.lab4.demo.item.patient;

import com.lab4.demo.item.model.dto.PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(PATIENTS)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> allPatients() {
        return patientService.findAll();
    }

    @PostMapping
    public PatientDTO create(@RequestBody PatientDTO patient) {
        return patientService.create(patient);
    }

    @PatchMapping(ENTITY)
    public PatientDTO changeName(@PathVariable Long id, @RequestBody String newName) {
        return patientService.changeName(id, newName);
    }

    @PatchMapping
    public PatientDTO update(@RequestBody PatientDTO patient) {
        return patientService.update(patient);
    }

    @GetMapping(ENTITY)
    public PatientDTO getPatient(@PathVariable Long id) {
        return patientService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { patientService.delete(id); }
}
