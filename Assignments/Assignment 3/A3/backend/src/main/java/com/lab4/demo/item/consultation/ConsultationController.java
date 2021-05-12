package com.lab4.demo.item.consultation;

import com.lab4.demo.item.model.dto.ConsultationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.CONSULTATIONS;
import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.DOCTOR_ENTITY;

@RestController
@RequestMapping(CONSULTATIONS)
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @GetMapping
    public List<ConsultationDTO> allConsultations() {
        return consultationService.findAll();
    }

    @PostMapping
    public ConsultationDTO create(@RequestBody ConsultationDTO consultation) {
        return consultationService.create(consultation);
    }

    @PatchMapping
    public ConsultationDTO update(@RequestBody ConsultationDTO consultation) {
        return consultationService.update(consultation);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id) { consultationService.delete(id); }

    @GetMapping(ENTITY)
    public ConsultationDTO getConsultation(@PathVariable Long id) {
        return consultationService.get(id);
    }

    @PatchMapping(DOCTOR_ENTITY)
    public ConsultationDTO updateDescription(@RequestBody ConsultationDTO consultation) {
        return consultationService.updateDescription(consultation);
    }
}
