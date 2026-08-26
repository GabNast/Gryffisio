package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SessionTypeCategoryDto;
import org.generation.italy.model.dto.SessionTypeCategoryRequest;
import org.generation.italy.services.SessionTypeCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session_type_categories")
public class SessionTypeCategoryController {
    private final SessionTypeCategoryService sessionTypeCategoryService;

    public SessionTypeCategoryController(SessionTypeCategoryService sessionTypeCategoryService) {
        this.sessionTypeCategoryService = sessionTypeCategoryService;
    }

    @GetMapping("/{id}")
    public SessionTypeCategoryDto getSessionTypeCategoryById(@PathVariable Integer id) {
        return sessionTypeCategoryService.findById(id);
    }

    @GetMapping
    public List<SessionTypeCategoryDto> getAllSessionTypeCategories() {
        return sessionTypeCategoryService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionTypeCategoryDto createSessionTypeCategory(@Valid @RequestBody SessionTypeCategoryRequest request) {
        return sessionTypeCategoryService.create(request);
    }

    @PutMapping("/{id}")
    public SessionTypeCategoryDto updateSessionTypeCategory(@PathVariable Integer id, @Valid @RequestBody SessionTypeCategoryRequest request) {
        return sessionTypeCategoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSessionTypeCategory(@PathVariable Integer id) {
        sessionTypeCategoryService.delete(id);
    }
}
