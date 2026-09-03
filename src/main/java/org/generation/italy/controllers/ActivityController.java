package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ActivityDto;
import org.generation.italy.model.dto.ActivityRequest;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.services.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/{id}")
    public ActivityDto getById(@PathVariable Integer id) throws NotFoundException {
        return activityService.findById(id);
    }

    @GetMapping
    public List<ActivityDto> getAll() {
        return activityService.findAll();
    }

    @GetMapping("/roots")
    public List<ActivityDto> getRoots() {
        return activityService.findRoots();
    }

    @GetMapping("/{parentId}/children")
    public List<ActivityDto> getChildren(@PathVariable Integer parentId) {
        return activityService.findChildren(parentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ActivityDto create(@Valid @RequestBody ActivityRequest request) throws NotFoundException {
        return activityService.createActivity(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ActivityDto update(@PathVariable Integer id, @Valid @RequestBody ActivityRequest request) throws NotFoundException {
        return activityService.updateActivity(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws NotFoundException {
        activityService.deleteActivity(id);
    }
}