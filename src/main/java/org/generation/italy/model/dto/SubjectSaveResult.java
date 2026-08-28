package org.generation.italy.model.dto;

public record SubjectSaveResult(
        SubjectDto subject,
        boolean codeAlreadyExists
) {}