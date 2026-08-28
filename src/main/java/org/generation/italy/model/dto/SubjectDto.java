package org.generation.italy.model.dto;

public record SubjectDto(
        Long id,
        Integer projectId,
        String projectName,
        String code,
        Integer subjectTypeId,
        String subjectTypeName
) {}