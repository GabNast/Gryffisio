package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.PatientDto;
import org.generation.italy.model.dto.PatientRequest;
import org.generation.italy.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public PatientDto findById(@PathVariable Long id) {
        return patientService.findById(id);
    }

    @GetMapping
    public List<PatientDto> findAllPatients() {
        return patientService.findAllPatients();
    }

    @GetMapping("/patientCode")
    public PatientDto findByPatientCode(@RequestParam Integer patientCode) {
        return patientService.findByCode(patientCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDto createPatient(@Valid @RequestBody PatientRequest patientRequest) {
        return patientService.createPatient(patientRequest);
    }

    @PutMapping("/{id}")
    public PatientDto updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest patientRequest) {
        return patientService.updatePatient(id, patientRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
    }
}
