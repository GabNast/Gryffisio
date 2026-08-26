package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SubjectTypeDto;
import org.generation.italy.model.dto.SubjectTypeRequest;
import org.generation.italy.services.SubjectTypeService;
import org.springframework.http.HttpStatus;
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
    public SubjectTypeDto getSubjectTypeById(@PathVariable Long id) {
        return subjectTypeService.findById(id);
    }

    @GetMapping("/search")
    public SubjectTypeDto getSubjectTypeByName(@RequestParam String typeName) {
        return subjectTypeService.findSubjectTypeName(typeName);
    }

    @GetMapping
    public List<SubjectTypeDto> findAll() {
        return subjectTypeService.findAllSubjectTypes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectTypeDto createSubjectType(@Valid @RequestBody SubjectTypeRequest subjectTypeRequest) {
        return  subjectTypeService.createSubjectType(subjectTypeRequest);
    }

    @PutMapping("/{id}")
    public SubjectTypeDto updateSubjectType(@PathVariable Long id, @Valid @RequestBody SubjectTypeRequest subjectTypeRequest) {
        return  subjectTypeService.updateSubjectType(id, subjectTypeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubjectType(@PathVariable Long id) {
        subjectTypeService.deleteSubjectType(id);
    }
}
