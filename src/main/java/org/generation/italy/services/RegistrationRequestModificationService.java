package org.generation.italy.services;

import org.generation.italy.model.dto.RegistrationRequestModificationDto;
import org.generation.italy.model.entities.Registration;
import org.generation.italy.model.entities.RegistrationRequestModification;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.RegistrationRepository;
import org.generation.italy.model.repositories.RegistrationRequestModificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RegistrationRequestModificationService {

    private final RegistrationRequestModificationRepository modificationRepository;
    private final RegistrationRepository registrationRepository;

    public RegistrationRequestModificationService(
            RegistrationRequestModificationRepository modificationRepository,
            RegistrationRepository registrationRepository) {
        this.modificationRepository = modificationRepository;
        this.registrationRepository = registrationRepository;
    }

    //Verifica se esiste la registrazione originale
    @Transactional
    public RegistrationRequestModificationDto createRequest(RegistrationRequestModificationDto requestDto) {
        Registration original = registrationRepository.findById(requestDto.registrationId())
                .orElseThrow(() -> new NotFoundException("Registration_not_found", "Registration not found: " + requestDto.registrationId()));

        //Set come richiesta in fase di attesa
        RegistrationRequestModification request = new RegistrationRequestModification();
        request.setRequestingResearcher(requestDto.requestingResearcherID()); //??
        request.setState('P'); // 'P' = perchè in attesa di risposta
        request.setRequestedDate(LocalDate.now());
        request.setDuration(requestDto.duration());
        request.setRegistration(original);
    }
}