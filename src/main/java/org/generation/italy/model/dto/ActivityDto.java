package org.generation.italy.model.dto;

public record ActivityDto(
        Integer id,
        String name,
        Integer parentId,
        String parentName
) {}