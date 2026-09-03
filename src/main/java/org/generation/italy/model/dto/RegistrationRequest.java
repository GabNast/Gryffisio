package org.generation.italy.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record RegistrationRequest(
        @NotNull
        Integer projectId,

        @NotNull
        Integer domainId,

        @NotNull
        Integer sessionId,

        Integer doctorId,

        @NotNull
        LocalDate activityDate,

        @NotNull
        Integer durationMinutes,

        @NotEmpty
        @Size(max = 5)
        List<Integer> operatorIds,

        @NotEmpty
        List<Long> subjectIds,

        @NotEmpty
        List<Integer> activityIds
) {}