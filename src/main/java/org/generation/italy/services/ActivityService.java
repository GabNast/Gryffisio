package org.generation.italy.services;

import org.generation.italy.model.dto.ActivityDto;
import org.generation.italy.model.dto.ActivityRequest;
import org.generation.italy.model.entities.Activity;
import org.generation.italy.model.exceptions.BadRequestException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    private ActivityDto toDto(Activity activity) {
        return new ActivityDto(
                activity.getId(),
                activity.getName(),
                activity.getParent() != null ? activity.getParent().getId() : null,
                activity.getParent() != null ? activity.getParent().getName() : null
        );
    }

    @Transactional(readOnly = true)
    public List<ActivityDto> findAll() {
        return activityRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ActivityDto findById(Integer id) throws NotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity_not_found", "Activity not found: " + id));
        return toDto(activity);
    }

    @Transactional(readOnly = true)
    public List<ActivityDto> findRoots() {
        return activityRepository.findByParentIsNull().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityDto> findChildren(Integer parentId) {
        return activityRepository.findByParent_Id(parentId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public ActivityDto createActivity(ActivityRequest request) throws NotFoundException {
        Activity parent = resolveParent(request.parentId(), null);

        Activity activity = new Activity();
        activity.setName(request.name());
        activity.setParent(parent);
        Activity saved = activityRepository.save(activity);
        return toDto(saved);
    }

    @Transactional
    public ActivityDto updateActivity(Integer id, ActivityRequest request) throws NotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Activity_not_found", "Activity not found: " + id));

        Activity parent = resolveParent(request.parentId(), id);

        activity.setName(request.name());
        activity.setParent(parent);
        return toDto(activityRepository.save(activity));
    }

    private Activity resolveParent(Integer parentId, Integer selfId) throws NotFoundException {
        if (parentId == null) {
            return null;
        }
        if (selfId != null && selfId.equals(parentId)) {
            throw new BadRequestException("Activity_invalid_parent", "An activity cannot be its own parent");
        }
        return activityRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Activity_not_found", "Parent activity not found: " + parentId));
    }

    @Transactional
    public void deleteActivity(Integer id) throws NotFoundException {
        if (!activityRepository.existsById(id)) {
            throw new NotFoundException("Activity_not_found", "Activity not found: " + id);
        }
        activityRepository.deleteById(id);
    }
}