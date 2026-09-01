package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ProjectDto;
import org.generation.italy.model.dto.ProjectRequest;
import org.generation.italy.services.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable Integer id) {
        return projectService.findById(id);
    }

    @GetMapping
    public List<ProjectDto> getAll() {
        return projectService.findAll();
    }

    @GetMapping("/name")
    public ProjectDto getByName(@RequestParam String name) {
        return projectService.findByName(name);
    }

    @GetMapping("/code")
    public ProjectDto getByCode(@RequestParam String code) {
        return projectService.findByCode(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto create(@Valid @RequestBody ProjectRequest request) {
        return projectService.createProject(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto update(@PathVariable Integer id, @Valid @RequestBody ProjectRequest request) {
        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }
}