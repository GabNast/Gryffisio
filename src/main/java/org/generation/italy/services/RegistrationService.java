package org.generation.italy.services;

import org.generation.italy.model.dto.RegistrationDto;
import org.generation.italy.model.dto.RegistrationRequest;
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
public class RegistrationService {
    private final RegistrationRepository registrationRepository;
    private final DomainRepository domainRepository;
    private final ProjectRepository projectRepository;
    private final PatientRepository patientRepository;
    private final ResearcherRepository researcherRepository;
    private final ReferringDoctorRepository referringDoctorRepository;
    private final TestRepository testRepository;

    public RegistrationService(
            RegistrationRepository registrationRepository,
            DomainRepository domainRepository,
            ProjectRepository projectRepository,
            PatientRepository patientRepository,
            ResearcherRepository researcherRepository,
            ReferringDoctorRepository referringDoctorRepository,
            TestRepository testRepository
    ) {
        this.registrationRepository = registrationRepository;
        this.domainRepository = domainRepository;
        this.projectRepository = projectRepository;
        this.patientRepository = patientRepository;
        this.researcherRepository = researcherRepository;
        this.referringDoctorRepository = referringDoctorRepository;
        this.testRepository = testRepository;
    }

    private RegistrationDto toDto(Registration registration) {
        return new RegistrationDto(
                registration.getId(),
                registration.getDomain().getId(),
                registration.getDomain().getName(),
                registration.getDate(),
                registration.getDurationMinutes(),
                registration.getProject().getId(),
                registration.getProject().getName(),
                registration.getPatient().getId(),
                registration.getPatient().getPatientCode(),
                registration.getPatients().stream().map(Patient::getId).toList(),
                registration.getResearchers().stream().map(Researcher::getId).toList(),
                registration.getReferringDoctor() != null ? registration.getReferringDoctor().getId() : null,
                registration.getReferringDoctor() != null ? registration.getReferringDoctor().getName() : null,
                registration.getTests().stream().map(Test::getId).toList(),
                registration.getCreationDate(),
                registration.getModified()
        );
    }

    @Transactional(readOnly = true)
    public List<RegistrationDto> findAll() {
        return registrationRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegistrationDto findById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registration_not_found", "Registration not found: " + id));
        return toDto(registration);
    }

    @Transactional
    public RegistrationDto createRegistration(RegistrationRequest request) {
        Registration registration = new Registration();
        applyRequest(registration, request);
        registration.setCreationDate(LocalDateTime.now());
        registration.setModified(false);
        Registration saved = registrationRepository.save(registration);
        return toDto(saved);
    }

    @Transactional
    public RegistrationDto updateRegistration(Long id, RegistrationRequest request) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Registration_not_found", "Registration not found: " + id));
        applyRequest(registration, request);
        registration.setLastModifiedDate(LocalDateTime.now());
        registration.setModified(true);
        return toDto(registrationRepository.save(registration));
    }

    // Metodo privato condiviso con Create e Update che evita di duplicare tutta la logica di risoluzione delle relazioni 2 volte.
    private void applyRequest(Registration registration, RegistrationRequest request) {
        Domain domain = domainRepository.findById(request.domainId())
                .orElseThrow(() -> new NotFoundException("Domain_not_found", "Domain not found: " + request.domainId()));

        Project project = projectRepository.findById(request.projectId())
                .orElseThrow(() -> new NotFoundException("Project_not_found", "Project not found: " + request.projectId()));

        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new NotFoundException("Patient_not_found", "Patient not found: " + request.patientId()));

        Set<Researcher> researchers = Set.copyOf(researcherRepository.findAllById(request.researcherIds()));
        if (researchers.size() != Set.copyOf(request.researcherIds()).size()) {
            throw new NotFoundException("Researcher_not_found", "One or more researcher ids do not exist");
        }

        Set<Test> tests = Set.copyOf(testRepository.findAllById(request.testIds()));
        if (tests.size() != Set.copyOf(request.testIds()).size()) {
            throw new NotFoundException("Test_not_found", "One or more test ids do not exist");
        }

        Set<Patient> additionalPatients = Set.of();
        if (request.additionalPatientIds() != null && !request.additionalPatientIds().isEmpty()) {
            if (request.additionalPatientIds().contains(request.patientId())) {
                throw new BadRequestException("Duplicate_patient_id", "Primary patient cannot also be listed as an additional patient");
            }
            additionalPatients = Set.copyOf(patientRepository.findAllById(request.additionalPatientIds()));
            if (additionalPatients.size() != Set.copyOf(request.additionalPatientIds()).size()) {
                throw new NotFoundException("Patient_not_found", "One or more additional patient ids do not exist");
            }
        }

        ReferringDoctor referringDoctor = null;
        if (request.referringDoctorId() != null) {
            referringDoctor = referringDoctorRepository.findById(request.referringDoctorId())
                    .orElseThrow(() -> new NotFoundException("Referring_doctor_not_found", "Referring doctor not found: " + request.referringDoctorId()));
        }

        registration.setDomain(domain);
        registration.setProject(project);
        registration.setPatient(patient);
        registration.setResearchers(researchers);
        registration.setTests(tests);
        registration.setPatients(additionalPatients);
        registration.setReferringDoctor(referringDoctor);
        registration.setDate(request.date());
        registration.setDurationMinutes(request.durationMinutes());
    }

    @Transactional
    public void deleteRegistration(Long id) {
        if (!registrationRepository.existsById(id)) {
            throw new NotFoundException("Registration_not_found", "Registration not found: " + id);
        }
        registrationRepository.deleteById(id);
    }
}