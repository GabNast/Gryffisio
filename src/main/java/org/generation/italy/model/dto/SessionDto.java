package org.generation.italy.model.dto;

public record SessionDto (
        Integer id,
        String evaluation,
        Integer sessionTypeId,
        String sessionTypeName
){}
