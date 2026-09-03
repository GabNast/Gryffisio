package org.generation.italy.services;

import org.generation.italy.model.dto.ModificationRequestDto;
import org.generation.italy.model.entities.ModificationRequest;
import org.generation.italy.model.entities.Operator;
import org.generation.italy.model.entities.Registration;
import org.generation.italy.model.exceptions.BadRequestException;
import org.generation.italy.model.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ModificationRequestServiceTest {

    @Mock
    private ModificationRequestRepository modificationRequestRepository;
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private OperatorRepository operatorRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private AuditLogService auditLogService;

    private ModificationRequestService modificationRequestService;

    @BeforeEach
    void setUp() {
        modificationRequestService = new ModificationRequestService(
                modificationRequestRepository,
                registrationRepository,
                operatorRepository,
                sessionRepository,
                doctorRepository,
                auditLogService
        );
    }

    @Test
    void findAll_withInvalidStatus_throwsBadRequestException() {
        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> modificationRequestService.findAll("boh"));

        assertEquals("Invalid_status", ex.getErrorCode());
        verify(modificationRequestRepository, never()).findByStatus(any());
        verify(modificationRequestRepository, never()).findAll();
    }

    @Test
    void findAll_withValidStatus_returnsOnlyRequestsWithThatStatus() {
        ModificationRequest approved = buildModificationRequest(ModificationRequest.Status.APPROVED);
        when(modificationRequestRepository.findByStatus(ModificationRequest.Status.APPROVED))
                .thenReturn(List.of(approved));

        List<ModificationRequestDto> result = modificationRequestService.findAll("approved");

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(dto -> dto.status().equals("APPROVED")));
        verify(modificationRequestRepository).findByStatus(ModificationRequest.Status.APPROVED);
        verify(modificationRequestRepository, never()).findAll();
    }

    private ModificationRequest buildModificationRequest(ModificationRequest.Status status) {
        Operator operator = new Operator();
        operator.setId(1);
        operator.setFirstName("Mario");
        operator.setLastName("Rossi");

        Registration registration = new Registration();
        registration.setId(10L);

        ModificationRequest mr = new ModificationRequest();
        mr.setId(100L);
        mr.setRegistration(registration);
        mr.setOperator(operator);
        mr.setStatus(status);
        mr.setReason("test reason");
        mr.setSubmittedAt(LocalDateTime.now());
        return mr;
    }
}
