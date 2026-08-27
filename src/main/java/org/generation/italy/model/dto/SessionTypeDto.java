package org.generation.italy.model.dto;

public record SessionTypeDto(
        Integer id,
        String name,
        String code,
        Integer sessionTypeCategoryId
) {
}
