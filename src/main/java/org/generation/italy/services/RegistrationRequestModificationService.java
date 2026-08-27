package org.generation.italy.services;

import org.generation.italy.model.dto.RegistrationRequestEvaluationDto;
import org.generation.italy.model.dto.RegistrationRequestModificationDto;
import org.generation.italy.model.entities.Registration;
import org.generation.italy.model.entities.RegistrationRequestModification;
import org.generation.italy.model.entities.Researcher;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class RegistrationRequestModificationService {
    // Queste servono per i servizi al database se non ci sono con chi comunica? con nessuno || Segnato perchè all'inizio NON lo avevo messo
    private final RegistrationRequestModificationRepository modificationRepository;
    private final RegistrationRequestEvaluationRepository evaluationRepository;
    private final RegistrationRepository registrationRepository;
    private final ResearcherRepository researcherRepository;
    private final SessionRepository sessionRepository;
    private final SessionTypeRepository sessionTypeRepository;

    // Costruttori così li uso ez
    public RegistrationRequestModificationService(
            RegistrationRequestModificationRepository modificationRepository,
            RegistrationRequestEvaluationRepository evaluationRepository,
            RegistrationRepository registrationRepository,
            ResearcherRepository researcherRepository,
            SessionRepository sessionRepository,
            SessionTypeRepository sessionTypeRepository
    ) {
        this.modificationRepository = modificationRepository;
        this.evaluationRepository = evaluationRepository;
        this.registrationRepository = registrationRepository;
        this.researcherRepository = researcherRepository;
        this.sessionRepository = sessionRepository;
        this.sessionTypeRepository = sessionTypeRepository;
    }

    @Transactional
    public RegistrationRequestModificationDto createRequest(
            RegistrationRequestModificationDto requestDto) {
        //Controlliamo che esista la registrazione via id dato dall'utente
        Registration registration = registrationRepository.findById(requestDto.registrationId())
                .orElseThrow(() -> new NotFoundException(
                        "Registration_not_found",
                        "Registration not found: " + requestDto.registrationId()
                ));
        //Stessa cosa ma con il ricercatore che DEVE essere presente nel database
        Researcher researcher = researcherRepository.findById(requestDto.requestingResearcherId())
                .orElseThrow(() -> new NotFoundException(
                        "Researcher_not_found",
                        "Researcher not found: " + requestDto.requestingResearcherId()
                ));
        //Si crea l' "altra form" quella della modifica
        RegistrationRequestModification request = new RegistrationRequestModification();
        //Viene messa automaticamente in attesa
        request.setRequestingResearcher(researcher);
        request.setState('P');
        request.setRequestedDate(LocalDate.now());
        request.setDuration(requestDto.duration());
        request.setRegistration(registration);

        RegistrationRequestModification savedRequest =
                modificationRepository.save(request);
        //ora si parte con un mix di if e for perchè dobbiamo prendere una alla volta le Evaluation se non ci sono non si fa
        if (requestDto.evaluations() != null) {

            for (RegistrationRequestEvaluationDto evaluationDto
                    : requestDto.evaluations()) {

            }

        }