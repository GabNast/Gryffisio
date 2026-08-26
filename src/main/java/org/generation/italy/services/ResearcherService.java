package org.generation.italy.services;

import org.generation.italy.model.dto.ResearcherDto;
import org.generation.italy.model.dto.ResearcherRequest;
import org.generation.italy.model.entities.Researcher;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.ResearcherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResearcherService {
    private final ResearcherRepository researcherRepository;

    public ResearcherService(ResearcherRepository researcherRepository) {
        this.researcherRepository = researcherRepository;
    }

    private ResearcherDto toDto(Researcher researcher){
        return new ResearcherDto(researcher.getId(), researcher.getName(), researcher.getSurname(),
                researcher.isStudent(), researcher.isActive());
    }

    @Transactional(readOnly = true)
    public List<ResearcherDto> findAllResearchers(){
        return researcherRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ResearcherDto findById(Long id){
        Researcher researcher = researcherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Researcher_not_found", "Researcher not found: " + id));
        return toDto(researcher);
    }

    @Transactional
    public ResearcherDto createResearcher(ResearcherRequest request){
        if(researcherRepository.existsByNameIgnoreCaseAndSurnameIgnoreCase(request.name(), request.surname())){
            throw new ConflictException("Researcher_name_already_exists", "Researcher name already in use " + request.name());
        }

        Researcher researcher = new Researcher();
        researcher.setName(request.name());
        researcher.setSurname(request.surname());
        researcher.setStudent(request.student() != null ? request.student() : false);
        Researcher savedResearcher = researcherRepository.save(researcher);
        return toDto(savedResearcher);
    }

    @Transactional(readOnly = true)
    public List<ResearcherDto> findActiveResearchers(){
        return researcherRepository.findByIsActiveTrue().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public ResearcherDto updateResearcher(Long id, ResearcherRequest request){
        Researcher researcher = researcherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Researcher_not_found", "Researcher not found: " + id));

        if(researcherRepository.existsByNameIgnoreCaseAndSurnameIgnoreCaseAndIdNot(request.name(), request.surname(), id)){
            throw new ConflictException("Researcher_name_already_exists", "Researcher name already in use " + request.name());

        }
        researcher.setName(request.name());
        researcher.setSurname(request.surname());
        researcher.setStudent(request.student() != null ? request.student() : false);

        return toDto(researcherRepository.save(researcher));
    }

    @Transactional
    public void delete(Long id){
        if(!researcherRepository.existsById(id)){
            throw new NotFoundException("Researcher_not_found", "Researcher not found: " + id);
        }
        researcherRepository.deleteById(id);
    }

}
