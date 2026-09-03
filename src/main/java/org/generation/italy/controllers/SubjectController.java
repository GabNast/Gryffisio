package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SubjectDto;
import org.generation.italy.model.dto.SubjectRequest;
import org.generation.italy.model.dto.SubjectSaveResult;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.services.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/{id}")
    public SubjectDto getById(@PathVariable Long id) throws NotFoundException {
        return subjectService.findById(id);
    }

    @GetMapping
    public List<SubjectDto> getAll() {
        return subjectService.findAll();
    }

    @GetMapping("/by-project/{projectId}")
    public List<SubjectDto> getByProject(@PathVariable Integer projectId) {
        return subjectService.findByProjectId(projectId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectSaveResult create(@Valid @RequestBody SubjectRequest request) throws NotFoundException {
        return subjectService.createSubject(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SubjectSaveResult update(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) throws NotFoundException {
        return subjectService.updateSubject(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws NotFoundException {
        subjectService.deleteSubject(id);
    }
}