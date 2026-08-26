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
        return new ProjectDto(project.getId(), project.getName(), project.getAcronym());
    }

    @Transactional(readOnly = true)
    public List<ProjectDto> findAllProjects() {
       return projectRepository.findAll().stream()
               .map(this::toDto)
               .toList();
    }

    @Transactional(readOnly = true)
    public ProjectDto findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + id));
        return toDto(project);
    }


    @Transactional
    public ProjectDto createProject(ProjectRequest projectRequest) {
        if (projectRepository.existsByNameIgnoreCase(projectRequest.name())) {
            throw new ConflictException("Project_name_unavailable", "Project name already exists: " + projectRequest.name());
        }

        if (projectRepository.existsByAcronymIgnoreCase(projectRequest.acronym())) {
            throw new ConflictException("Project_acronym_unavailable", "Project acronym already exists: " + projectRequest.acronym());
        }
        Project project = new Project();
        project.setName(projectRequest.name());
        project.setAcronym(projectRequest.acronym());
        Project savedProject = projectRepository.save(project);
        return toDto(savedProject);
    }

    @Transactional
    public ProjectDto updateProject(Long id, ProjectRequest projectRequest) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + id));
        if (projectRepository.existsByNameIgnoreCaseAndIdNot(projectRequest.name(), id)) {
            throw new ConflictException("Project_name_unavailable", "Project name already exists: " + projectRequest.name());
        }
        if(projectRepository.existsByAcronymIgnoreCaseAndIdNot(projectRequest.acronym(), id)) {
            throw new ConflictException("Project_acronym_unavailable", "Project acronym already exists: " + projectRequest.acronym());
        }

        project.setName(projectRequest.name());
        project.setAcronym(projectRequest.acronym());
        return toDto(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        if(!projectRepository.existsById(id)) {
            throw new NotFoundException("Project_not_found", "Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}
