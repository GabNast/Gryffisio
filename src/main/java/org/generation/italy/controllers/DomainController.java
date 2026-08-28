package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.DomainDto;
import org.generation.italy.model.dto.DomainRequest;
import org.generation.italy.services.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
public class DomainController {
    private final DomainService domainService;

    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @GetMapping("/{id}")
    public DomainDto getById(@PathVariable Integer id) {
        return domainService.findById(id);
    }

    @GetMapping
    public List<DomainDto> getAll() {
        return domainService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DomainDto create(@Valid @RequestBody DomainRequest request) {
        return domainService.createDomain(request);
    }

    @PutMapping("/{id}")
    public DomainDto update(@PathVariable Integer id, @Valid @RequestBody DomainRequest request) {
        return domainService.updateDomain(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        domainService.deleteDomain(id);
    }
}