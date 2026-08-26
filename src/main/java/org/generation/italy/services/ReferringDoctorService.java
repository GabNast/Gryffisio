package org.generation.italy.services;

import org.generation.italy.model.dto.ReferringDoctorDto;
import org.generation.italy.model.dto.ReferringDoctorRequest;
import org.generation.italy.model.entities.ReferringDoctor;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.ReferringDoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReferringDoctorService {
    private final ReferringDoctorRepository referringDoctorRepository;

    public ReferringDoctorService(ReferringDoctorRepository referringDoctorRepository) {
        this.referringDoctorRepository = referringDoctorRepository;
    }

   private ReferringDoctorDto referringDoctorDto(ReferringDoctor referringDoctor){
        return  new ReferringDoctorDto(referringDoctor.getId(), referringDoctor.getName(), referringDoctor.getSurname(), referringDoctor.getGender());
   }

    @Transactional(readOnly = true)
    public List<ReferringDoctorDto> findAll(){
        return referringDoctorRepository.findAll().stream()
                .map(this::referringDoctorDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReferringDoctorDto findById(Long id){
        ReferringDoctor  referringDoctor = referringDoctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Referring_doctor_not_found", "Referring doctor not found with id " + id));
        return referringDoctorDto(referringDoctor);
    }

    @Transactional(readOnly = true)
    public ReferringDoctorDto findByNameAndSurname(String name, String surname){
        ReferringDoctor referringDoctor = referringDoctorRepository.findByNameIgnoreCaseAndSurnameIgnoreCase(name, surname)
                .orElseThrow(() -> new NotFoundException("Referring_doctor_not_found", "Referring doctor not found with name " + name + " and surname " + surname));
        return  referringDoctorDto(referringDoctor);
    }

    @Transactional
    public ReferringDoctorDto createReferringDoctor(ReferringDoctorRequest referringDoctorRequest){
        if(referringDoctorRepository.existsByNameIgnoreCaseAndSurnameIgnoreCase(referringDoctorRequest.name(), referringDoctorRequest.surname())){
            throw new ConflictException("Referring_doctor_already_exists", "Referring doctor already exists: " +  referringDoctorRequest.name() + " " + referringDoctorRequest.surname());
        }
        ReferringDoctor referringDoctor = new ReferringDoctor();
        referringDoctor.setName(referringDoctorRequest.name());
        referringDoctor.setSurname(referringDoctorRequest.surname());
        referringDoctor.setGender(referringDoctorRequest.gender());
        ReferringDoctor savedReferringDoctor = referringDoctorRepository.save(referringDoctor);
        return referringDoctorDto(savedReferringDoctor);
    }

    @Transactional
    public ReferringDoctorDto updateReferringDoctor(Long id, ReferringDoctorRequest referringDoctorRequest){
        ReferringDoctor referringDoctor = referringDoctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Referring_doctor_not_found", "Referring doctor not found with id " + id));

        if(referringDoctorRepository.existsByNameIgnoreCaseAndSurnameIgnoreCaseAndIdNot(referringDoctorRequest.name(), referringDoctorRequest.surname(), id)){
            throw new ConflictException("Referring_doctor_name_unavailable", "Referring doctor name already exists: " + referringDoctorRequest.name() + " " + referringDoctorRequest.surname());
        }

        referringDoctor.setName(referringDoctorRequest.name());
        referringDoctor.setSurname(referringDoctorRequest.surname());
        referringDoctor.setGender(referringDoctorRequest.gender());

        return referringDoctorDto(referringDoctorRepository.save(referringDoctor));
    }

    @Transactional
    public void deleteById(Long id){
        if(!referringDoctorRepository.existsById(id)){
            throw new NotFoundException("Referring_doctor_not_found", "Referring doctor not found: " + id);
        }
        referringDoctorRepository.deleteById(id);
    }
}
