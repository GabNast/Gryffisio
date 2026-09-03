package org.generation.italy.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record RegistrationDto(
        Long id,
        Integer projectId,
        String projectName,
        Integer domainId,
        String domainName,
        Integer sessionId,
        String sessionName,
        Integer doctorId,
        String doctorFullName,
        LocalDate activityDate,
        Integer durationMinutes,
        List<Integer> operatorIds,
        List<Long> subjectIds,
        List<Integer> activityIds,
        LocalDateTime createdAt
) {}