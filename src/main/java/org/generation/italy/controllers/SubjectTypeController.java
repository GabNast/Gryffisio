package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SubjectTypeDto;
import org.generation.italy.model.dto.SubjectTypeRequest;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.services.SubjectTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject-types")
public class SubjectTypeController {
    private final SubjectTypeService subjectTypeService;

    public SubjectTypeController(SubjectTypeService subjectTypeService) {
        this.subjectTypeService = subjectTypeService;
    }

    @GetMapping("/{id}")
    public SubjectTypeDto getById(@PathVariable Integer id) throws NotFoundException {
        return subjectTypeService.findById(id);
    }

    @GetMapping
    public List<SubjectTypeDto> getAll() {
        return subjectTypeService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectTypeDto create(@Valid @RequestBody SubjectTypeRequest request) {
        return subjectTypeService.createSubjectType(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectTypeDto update(@PathVariable Integer id, @Valid @RequestBody SubjectTypeRequest request) throws NotFoundException {
        return subjectTypeService.updateSubjectType(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws NotFoundException {
        subjectTypeService.deleteSubjectType(id);
    }
}