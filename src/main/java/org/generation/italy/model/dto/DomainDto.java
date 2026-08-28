package org.generation.italy.model.dto;

import java.util.List;

public record DomainDto(
        Integer id,
        String name,
        List<Integer> activityIds,
        List<String> activityNames
) {}