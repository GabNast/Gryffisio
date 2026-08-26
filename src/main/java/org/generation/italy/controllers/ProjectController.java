package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ProjectDto;
import org.generation.italy.model.dto.ProjectRequest;
import org.generation.italy.services.ProjectService;
import org.springframework.http.HttpStatus;
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
    public ProjectDto getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return  projectService.findAllProjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(@Valid @RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequest projectRequest) {
        return projectService.updateProject(id, projectRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

}
