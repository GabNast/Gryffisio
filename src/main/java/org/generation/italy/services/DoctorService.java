package org.generation.italy.services;

import org.generation.italy.model.dto.DoctorDto;
import org.generation.italy.model.dto.DoctorRequest;
import org.generation.italy.model.entities.Doctor;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    private DoctorDto toDto(Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName());
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> findAll() {
        return doctorRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DoctorDto findById(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor_not_found", "Doctor not found: " + id));
        return toDto(doctor);
    }

    @Transactional(readOnly = true)
    public List<DoctorDto> findByLastName(String lastName) {
        return doctorRepository.findByLastNameIgnoreCase(lastName).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public DoctorDto createDoctor(DoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        Doctor saved = doctorRepository.save(doctor);
        return toDto(saved);
    }

    @Transactional
    public DoctorDto updateDoctor(Integer id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor_not_found", "Doctor not found: " + id));

        doctor.setFirstName(request.firstName());
        doctor.setLastName(request.lastName());
        return toDto(doctorRepository.save(doctor));
    }

    @Transactional
    public void deleteDoctor(Integer id) {
        if (!doctorRepository.existsById(id)) {
            throw new NotFoundException("Doctor_not_found", "Doctor not found: " + id);
        }
        doctorRepository.deleteById(id);
    }
}