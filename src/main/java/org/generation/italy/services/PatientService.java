package org.generation.italy.services;

import org.generation.italy.model.dto.PatientDto;
import org.generation.italy.model.dto.PatientRequest;
import org.generation.italy.model.entities.Patient;
import org.generation.italy.model.entities.SubjectType;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.PatientRepository;
import org.generation.italy.model.repositories.SubjectTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final SubjectTypeRepository subjectTypeRepository;

    public PatientService(PatientRepository patientRepository,  SubjectTypeRepository subjectTypeRepository) {
        this.patientRepository = patientRepository;
        this.subjectTypeRepository = subjectTypeRepository;
    }

    private PatientDto patientDto(Patient patient) {
        return new PatientDto(patient.getId(), patient.getPatientCode(), patient.getSubjectType().getId(), patient.getSubjectType().getType());
    }

    @Transactional(readOnly = true)
    public List<PatientDto> findAllPatients() {
        return patientRepository.findAll().stream().map(this::patientDto).toList();
    }

    @Transactional(readOnly = true)
    public PatientDto findById(Long id) {
        return patientDto(patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient_id_not_found", "Patient id not found: " + id)));
    }

    @Transactional(readOnly = true)
    public PatientDto findByCode(Integer code) {
        Patient patient = patientRepository.findByPatientCode(code)
                .orElseThrow(() -> new NotFoundException("Patient_code_not_found", "Patient code not found: " + code));
        return patientDto(patient);
    }

    @Transactional
    public PatientDto createPatient(PatientRequest request) {

        // qui il controllo del conflitto manca per la specifica del "Se l'ID è già presente, mostrare un avviso informativo senza bloccare l'inserimento."

        SubjectType subjectType = subjectTypeRepository.findById(request.subjectTypeId())
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + request.subjectTypeId()));

        Patient patient = new Patient();
        patient.setPatientCode(request.patientCode());
        patient.setSubjectType(subjectType);
        Patient saved = patientRepository.save(patient);
        return patientDto(saved);
    }

    @Transactional
    public PatientDto updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient_not_found", "Patient not found: " + id));

        if (patientRepository.existsByPatientCodeAndIdNot(request.patientCode(), id)) {
            throw new ConflictException("Patient_code_already_exists", "Patient code already exists: " + request.patientCode());
        }

        SubjectType subjectType = subjectTypeRepository.findById(request.subjectTypeId())
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + request.subjectTypeId()));

        patient.setPatientCode(request.patientCode());
        patient.setSubjectType(subjectType);
        return patientDto(patientRepository.save(patient));
    }

    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new NotFoundException("Patient_not_found", "Patient not found: " + id);
        }
        patientRepository.deleteById(id);
    }
}
