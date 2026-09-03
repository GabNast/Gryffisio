package org.generation.italy.services;

import org.generation.italy.model.dto.ProjectDto;
import org.generation.italy.model.dto.ProjectRequest;
import org.generation.italy.model.entities.Project;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private ProjectDto toDto(Project project) {
        return new ProjectDto(project.getId(), project.getName(), project.getCode());
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjectDto findById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + id));
        return toDto(project);
    }

    @Transactional(readOnly = true)
    public ProjectDto findByCode(String code) {
        Project project = projectRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + code));
        return toDto(project);
    }

    @Transactional(readOnly = true)
    public ProjectDto findByName(String name) {
        Project project = projectRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + name));
        return toDto(project);
    }

    @Transactional
    public ProjectDto createProject(ProjectRequest request) {
        if (projectRepository.existsByNameIgnoreCase(request.name())) {
            throw new ConflictException("Project_name_already_exists", "Project name already exists: " + request.name());
        }
        if (projectRepository.existsByCodeIgnoreCase(request.code())) {
            throw new ConflictException("Project_code_already_exists", "Project code already exists: " + request.code());
        }

        Project project = new Project();
        project.setName(request.name());
        project.setCode(request.code());
        Project saved = projectRepository.save(project);
        return toDto(saved);
    }

    @Transactional
    public ProjectDto updateProject(Integer id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + id));

        if (projectRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new ConflictException("Project_name_already_exists", "Project name already exists: " + request.name());
        }
        if (projectRepository.existsByCodeIgnoreCaseAndIdNot(request.code(), id)) {
            throw new ConflictException("Project_code_already_exists", "Project code already exists: " + request.code());
        }

        project.setName(request.name());
        project.setCode(request.code());
        return toDto(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new NotFoundException("Project_not_found", "Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}