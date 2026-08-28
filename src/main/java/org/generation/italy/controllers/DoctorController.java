package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.DoctorDto;
import org.generation.italy.model.dto.DoctorRequest;
import org.generation.italy.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{id}")
    public DoctorDto getById(@PathVariable Integer id) {
        return doctorService.findById(id);
    }

    @GetMapping("/search")
    public List<DoctorDto> searchByLastName(@RequestParam String lastName) {
        return doctorService.findByLastName(lastName);
    }

    @GetMapping
    public List<DoctorDto> getAll() {
        return doctorService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@Valid @RequestBody DoctorRequest request) {
        return doctorService.createDoctor(request);
    }

    @PutMapping("/{id}")
    public DoctorDto update(@PathVariable Integer id, @Valid @RequestBody DoctorRequest request) {
        return doctorService.updateDoctor(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
    }
}