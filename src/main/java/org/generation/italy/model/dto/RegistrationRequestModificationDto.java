package org.generation.italy.model.dto;

import java.time.LocalDate;
import java.util.List;

public record RegistrationRequestModificationDto(
        Long id,
        Long requestingResearcherId, //riga 18, perchè così si prende SOLO l'id del ricercatore
        Character state,
        LocalDate requestedDate,
       // Long adminManagerId || riga 30,per vedere chi degli admin ha approvato/disapprovato: no perchè se no l'utente decide l'admin, grz chia
        Integer duration,
        Long registrationId,
        List<RegistrationRequestEvaluationDto> evaluations
) {}
