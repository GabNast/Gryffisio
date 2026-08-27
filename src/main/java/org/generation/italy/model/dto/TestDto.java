package org.generation.italy.model.dto;

import java.util.List;

public record TestDto(
        Long id,
        String name,
        List<Long> domainIds,
        List<String> domainNames
) {}