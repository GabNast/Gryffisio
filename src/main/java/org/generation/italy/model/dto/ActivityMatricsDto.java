package org.generation.italy.model.dto;

public record ActivityMatricsDto(
        Integer activityId,
        String activityName,
        long registrationCount,
        double totalHours
) {}
