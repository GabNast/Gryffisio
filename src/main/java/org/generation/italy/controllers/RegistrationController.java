package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.RegistrationDto;
import org.generation.italy.model.dto.RegistrationRequest;
import org.generation.italy.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/{id}")
    public RegistrationDto getById(@PathVariable Long id) {
        return registrationService.findById(id);
    }

    @GetMapping
    public List<RegistrationDto> getAll() {
        return registrationService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDto create(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.createRegistration(request);
    }

    @PutMapping("/{id}")
    public RegistrationDto update(@PathVariable Long id, @Valid @RequestBody RegistrationRequest request) {
        return registrationService.updateRegistration(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
    }
}