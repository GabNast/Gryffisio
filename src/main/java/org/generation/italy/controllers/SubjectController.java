package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SubjectDto;
import org.generation.italy.model.dto.SubjectRequest;
import org.generation.italy.model.dto.SubjectSaveResult;
import org.generation.italy.services.SubjectService;
import org.springframework.http.HttpStatus;
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
    public SubjectDto getById(@PathVariable Long id) {
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
    public SubjectSaveResult create(@Valid @RequestBody SubjectRequest request) {
        return subjectService.createSubject(request);
    }

    @PutMapping("/{id}")
    public SubjectSaveResult update(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        return subjectService.updateSubject(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }
}