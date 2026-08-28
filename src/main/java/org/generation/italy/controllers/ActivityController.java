package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ActivityDto;
import org.generation.italy.model.dto.ActivityRequest;
import org.generation.italy.services.ActivityService;
import org.springframework.http.HttpStatus;
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
    public ActivityDto getById(@PathVariable Integer id) {
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
    public ActivityDto create(@Valid @RequestBody ActivityRequest request) {
        return activityService.createActivity(request);
    }

    @PutMapping("/{id}")
    public ActivityDto update(@PathVariable Integer id, @Valid @RequestBody ActivityRequest request) {
        return activityService.updateActivity(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        activityService.deleteActivity(id);
    }
}