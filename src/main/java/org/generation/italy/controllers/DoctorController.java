package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.DoctorDto;
import org.generation.italy.model.dto.DoctorRequest;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public DoctorDto getById(@PathVariable Integer id) throws NotFoundException {
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
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorDto create(@Valid @RequestBody DoctorRequest request) {
        return doctorService.createDoctor(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorDto update(@PathVariable Integer id, @Valid @RequestBody DoctorRequest request) throws NotFoundException {
        return doctorService.updateDoctor(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws NotFoundException {
        doctorService.deleteDoctor(id);
    }
}