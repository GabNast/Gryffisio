package org.generation.italy.services;

import org.generation.italy.model.dto.SessionTypeCategoryRequest;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.generation.italy.model.dto.SessionTypeCategoryDto;
import org.generation.italy.model.entities.SessionTypeCategory;
import org.generation.italy.model.repositories.SessionTypeCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionTypeCategoryService {
    private final SessionTypeCategoryRepository sessionTypeCategoryRepository;

    public SessionTypeCategoryService(SessionTypeCategoryRepository sessionTypeCategoryRepository) {
        this.sessionTypeCategoryRepository = sessionTypeCategoryRepository;
    }

    private SessionTypeCategoryDto toDto(SessionTypeCategory dto) {
        return new SessionTypeCategoryDto(dto.getId(), dto.getName());
    }

    @Transactional(readOnly = true)
    public List<SessionTypeCategoryDto> findAll(){
        return sessionTypeCategoryRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SessionTypeCategoryDto findById(Integer id){
        SessionTypeCategory sessionTypeCategory = sessionTypeCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_type_category_not_found", "Session type category not found" + id));
        return toDto(sessionTypeCategory);
    }

    @Transactional
    public SessionTypeCategoryDto create(SessionTypeCategoryRequest request){
        if(sessionTypeCategoryRepository.existsByNameIgnoreCase(request.name())){
            throw new ConflictException("Session_type_category_name_already_exists", "Session type category name already exists" + request.name());
        }
        SessionTypeCategory sessionTypeCategory = new SessionTypeCategory();
        sessionTypeCategory.setName(request.name());
        SessionTypeCategory saved = sessionTypeCategoryRepository.save(sessionTypeCategory);
        return toDto(saved);
    }

    @Transactional
    public SessionTypeCategoryDto update(Integer id, SessionTypeCategoryRequest request){
        SessionTypeCategory sessionTypeCategory = sessionTypeCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_type_category_not_found", "Session type category not found" + id));

        if(sessionTypeCategoryRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)){
            throw new ConflictException("Session_type_category_name_already_exists", "Session type category name already exists" + id);
        }
        sessionTypeCategory.setName(request.name());
        return toDto(sessionTypeCategoryRepository.save(sessionTypeCategory));
    }

    @Transactional
    public void delete(Integer id){
        if(!sessionTypeCategoryRepository.existsById(id)){
            throw new NotFoundException("Session_type_category_not_found", "Session type category not found" + id);
        }
        sessionTypeCategoryRepository.deleteById(id);
    }

}
