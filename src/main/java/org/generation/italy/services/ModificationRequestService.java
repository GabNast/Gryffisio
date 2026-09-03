package org.generation.italy.services;

import org.generation.italy.model.dto.ModificationRequestDecision;
import org.generation.italy.model.dto.ModificationRequestDto;
import org.generation.italy.model.dto.ModificationRequestRequest;
import org.generation.italy.model.entities.*;
import org.generation.italy.model.exceptions.BadRequestException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class ModificationRequestService {
    private final ModificationRequestRepository modificationRequestRepository;
    private final RegistrationRepository registrationRepository;
    private final OperatorRepository operatorRepository;
    private final SessionRepository sessionRepository;
    private final DoctorRepository doctorRepository;

    private final AuditLogService auditLogService;

    public ModificationRequestService(
            ModificationRequestRepository modificationRequestRepository,
            RegistrationRepository registrationRepository,
            OperatorRepository operatorRepository,
            SessionRepository sessionRepository,
            DoctorRepository doctorRepository, AuditLogService auditLogService
    ) {
        this.modificationRequestRepository = modificationRequestRepository;
        this.registrationRepository = registrationRepository;
        this.operatorRepository = operatorRepository;
        this.sessionRepository = sessionRepository;
        this.doctorRepository = doctorRepository;
        this.auditLogService = auditLogService;
    }

    private ModificationRequestDto toDto(ModificationRequest mr) {
        Operator handledBy = mr.getHandledByAdmin();
        return new ModificationRequestDto(
                mr.getId(),
                mr.getRegistration().getId(),
                mr.getOperator().getId(),
                mr.getOperator().getFirstName() + " " + mr.getOperator().getLastName(),
                mr.getNewActivityDate(),
                mr.getNewDurationMinutes(),
                mr.getNewSession() != null ? mr.getNewSession().getId() : null,
                mr.getNewDoctor() != null ? mr.getNewDoctor().getId() : null,
                mr.getReason(),
                mr.getStatus().name(),
                mr.getSubmittedAt(),
                handledBy != null ? handledBy.getId() : null,
                mr.getHandledAt(),
                mr.getRejectionReason()
        );
    }

    @Transactional(readOnly = true)
    public List<ModificationRequestDto> findAll(String status) {
        if (status == null) {
            return modificationRequestRepository.findAll().stream()
                    .map(this::toDto)
                    .toList();
        }

        ModificationRequest.Status parsedStatus;
        try {
            parsedStatus = ModificationRequest.Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid_status", "Invalid status: " + status + ". Allowed values: PENDING, APPROVED, REJECTED");
        }

        return modificationRequestRepository.findByStatus(parsedStatus).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ModificationRequestDto findById(Long id) throws NotFoundException {
        ModificationRequest mr = modificationRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modification_request_not_found", "Modification request not found: " + id));
        return toDto(mr);
    }

    @Transactional
    public ModificationRequestDto create(ModificationRequestRequest request) throws NotFoundException {
        Registration registration = registrationRepository.findById(request.registrationId())
                .orElseThrow(() -> new NotFoundException("Registration_not_found", "Registration not found: " + request.registrationId()));

        Operator operator = operatorRepository.findById(request.operatorId())
                .orElseThrow(() -> new NotFoundException("Operator_not_found", "Operator not found: " + request.operatorId()));

        Session newSession = null;
        if (request.newSessionId() != null) {
            newSession = sessionRepository.findById(request.newSessionId())
                    .orElseThrow(() -> new NotFoundException("Session_not_found", "Session not found: " + request.newSessionId()));
        }

        Doctor newDoctor = null;
        if (request.newDoctorId() != null) {
            newDoctor = doctorRepository.findById(request.newDoctorId())
                    .orElseThrow(() -> new NotFoundException("Doctor_not_found", "Doctor not found: " + request.newDoctorId()));
        }

        ModificationRequest mr = new ModificationRequest();
        mr.setRegistration(registration);
        mr.setOperator(operator);
        mr.setNewActivityDate(request.newActivityDate());
        mr.setNewDurationMinutes(request.newDurationMinutes());
        mr.setNewSession(newSession);
        mr.setNewDoctor(newDoctor);
        mr.setReason(request.reason());
        mr.setStatus(ModificationRequest.Status.PENDING);
        mr.setSubmittedAt(LocalDateTime.now());

        ModificationRequest saved = modificationRequestRepository.save(mr);
        return toDto(saved);
    }

    @Transactional
    public ModificationRequestDto decide(Long id, Integer adminId, ModificationRequestDecision decision) throws NotFoundException {
        ModificationRequest mr = modificationRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modification_request_not_found", "Modification request not found: " + id));

        if (mr.getStatus() != ModificationRequest.Status.PENDING) {
            throw new BadRequestException("Modification_request_already_handled", "This modification request has already been handled");
        }

        Operator admin = operatorRepository.findById(adminId)
                .orElseThrow(() -> new NotFoundException("Operator_not_found", "Operator not found: " + adminId));

        if (Boolean.TRUE.equals(decision.approve())) {
            Registration registration = mr.getRegistration();

            if (mr.getNewActivityDate() != null) {
                registration.setActivityDate(mr.getNewActivityDate());
            }
            if (mr.getNewDurationMinutes() != null) {
                registration.setDurationMinutes(mr.getNewDurationMinutes());
            }
            if (mr.getNewSession() != null) {
                registration.setSession(mr.getNewSession());
            }
            if (mr.getNewDoctor() != null) {
                registration.setDoctor(mr.getNewDoctor());
            }
            registrationRepository.save(registration);

            mr.setStatus(ModificationRequest.Status.APPROVED);
        } else {
            mr.setStatus(ModificationRequest.Status.REJECTED);
            mr.setRejectionReason(decision.rejectionReason());
        }

        mr.setHandledByAdmin(admin);
        mr.setHandledAt(LocalDateTime.now());

        String action = Boolean.TRUE.equals(decision.approve()) ? "APPROVE_MODIFICATION" : "REJECT_MODIFICATION";
        auditLogService.log(Set.of(admin), action, "ModificationRequest", mr.getId(), mr.getReason());

        return toDto(modificationRequestRepository.save(mr));
    }
}