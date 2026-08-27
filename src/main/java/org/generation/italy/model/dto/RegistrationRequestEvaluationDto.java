package org.generation.italy.model.dto;
//Lo vede l'utente non l'admin
//Serve per i dati che l'utente manda per modificare la form
public record RegistrationRequestEvaluationDto(
        Integer sessionId,
        Integer sessionTypeId
) {}
