package org.generation.italy.services;

import org.generation.italy.model.dto.SessionTypeDto;
import org.generation.italy.model.dto.SessionTypeRequest;
import org.generation.italy.model.entities.SessionType;
import org.generation.italy.model.entities.SessionTypeCategory;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.SessionTypeCategoryRepository;
import org.generation.italy.model.repositories.SessionTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SessionTypeService {
    private final SessionTypeRepository sessionTypeRepository;
    private final SessionTypeCategoryRepository sessionTypeCategoryRepository;

    public SessionTypeService(SessionTypeRepository sessionTypeRepository, SessionTypeCategoryRepository sessionTypeCategoryRepository) {
        this.sessionTypeRepository = sessionTypeRepository;
        this.sessionTypeCategoryRepository = sessionTypeCategoryRepository;
    }

    private SessionTypeDto sessionTypeDto(SessionType sessionType) {
        return new SessionTypeDto(sessionType.getId(), sessionType.getName(), sessionType.getCode(), sessionType.getSessionTypeCategory().getId());
    }

    @Transactional(readOnly = true)
    public List<SessionTypeDto> findAll() {
        return sessionTypeRepository.findAll().stream().map(this::sessionTypeDto).toList();
    }

    @Transactional(readOnly = true)
    public SessionTypeDto findById(Integer id) {
        return sessionTypeDto(sessionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_type_not_found", "Session type not found: " + id)));
    }

    @Transactional(readOnly = true)
    public SessionTypeDto findByCodeIgnoreCase(String code) {
        SessionType sessionType = sessionTypeRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new NotFoundException("Session_type_code_not_found", "Session type code not found: " + code));
        return sessionTypeDto(sessionType);
    }

    @Transactional
    public SessionTypeDto createSessionType(SessionTypeRequest sessionTypeRequest) {
        if(sessionTypeRepository.existsByNameIgnoreCase(sessionTypeRequest.name())){
            throw new ConflictException("Session_type_name_already_exists", "Session type name already exists" +  sessionTypeRequest.name());
        }
        if (sessionTypeRepository.existsByCodeIgnoreCase(sessionTypeRequest.code())) {
            throw new ConflictException("Session_type_code_already_exists", "Session type code already exists: " + sessionTypeRequest.code());
        }

        SessionTypeCategory category = sessionTypeCategoryRepository.findById(sessionTypeRequest.sessionTypeCategoryId())
                .orElseThrow(() -> new NotFoundException("Session_type_category_not_found", "Session type category not found: " + sessionTypeRequest.sessionTypeCategoryId()));

        SessionType sessionType = new SessionType();
        sessionType.setName(sessionTypeRequest.name());
        sessionType.setCode(sessionTypeRequest.code());
        sessionType.setSessionTypeCategory(category);
        SessionType  savedSessionType = sessionTypeRepository.save(sessionType);
        return sessionTypeDto(savedSessionType);
    }

    @Transactional
    public SessionTypeDto updateSessionType(Integer id, SessionTypeRequest sessionTypeRequest) {
        SessionType sessionType = sessionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Session_type_not_found", "Session type not found: " + id));

        if(sessionTypeRepository.existsByNameIgnoreCaseAndIdNot(sessionTypeRequest.name(), id)){
            throw new ConflictException("Session_name_unavailable", "Session type name already exists" +  sessionTypeRequest.name());
        }

        if(sessionTypeRepository.existsByCodeIgnoreCaseAndIdNot(sessionTypeRequest.code(), id)){
            throw new ConflictException("Session_code_unavailable", "Session type code already exists" +  sessionTypeRequest.code());
        }

        SessionTypeCategory category = sessionTypeCategoryRepository.findById(sessionTypeRequest.sessionTypeCategoryId())
                .orElseThrow(() -> new NotFoundException("Session_type_category_not_found", "Session type category not found: " + sessionTypeRequest.sessionTypeCategoryId()));

        sessionType.setName(sessionTypeRequest.name());
        sessionType.setCode(sessionTypeRequest.code());
        sessionType.setSessionTypeCategory(category);
        return sessionTypeDto(sessionTypeRepository.save(sessionType));
    }

    @Transactional
    public void deleteSessionType(Integer id) {
        if(!sessionTypeRepository.existsById(id)){
            throw new NotFoundException("Session_type_not_found", "Session type not found: " + id);
        }
        sessionTypeRepository.deleteById(id);
    }

}
