package org.generation.italy.services;

import org.generation.italy.model.dto.SubjectTypeDto;
import org.generation.italy.model.dto.SubjectTypeRequest;
import org.generation.italy.model.entities.SubjectType;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.SubjectTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectTypeService {
    private final SubjectTypeRepository subjectTypeRepository;

    public SubjectTypeService(SubjectTypeRepository subjectTypeRepository) {
        this.subjectTypeRepository = subjectTypeRepository;
    }

    private SubjectTypeDto subjectTypeDto(SubjectType subjectType) {
        return new SubjectTypeDto(subjectType.getId(), subjectType.getType(), subjectType.getDescription());
    }

    @Transactional(readOnly = true)
    public List<SubjectTypeDto> findAllSubjectTypes() {
        return subjectTypeRepository.findAll().stream()
                .map(this::subjectTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubjectTypeDto findSubjectTypeName(String typeName) {
        SubjectType subjectType = subjectTypeRepository.findByTypeIgnoreCase(typeName)
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + typeName));
        return subjectTypeDto(subjectType);
    }

    @Transactional(readOnly = true)
    public SubjectTypeDto findById(Long id) {
        SubjectType subjectType = subjectTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + id));
        return subjectTypeDto(subjectType);
    }

    @Transactional
    public SubjectTypeDto createSubjectType(SubjectTypeRequest request) {
        if(subjectTypeRepository.existsByTypeIgnoreCase(request.type())){
            throw new ConflictException("Subject_type_already_exists", "Subject type already exists: " + request.type());
        }

        SubjectType subjectType = new SubjectType();
        subjectType.setType(request.type());
        SubjectType saveSubjectType = subjectTypeRepository.save(subjectType);
        return subjectTypeDto(saveSubjectType);
    }

    @Transactional
    public SubjectTypeDto updateSubjectType(Long id, SubjectTypeRequest subjectTypeRequest) {
        SubjectType subjectType = subjectTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + id));

        if(subjectTypeRepository.existsByTypeIgnoreCaseAndIdNot(subjectTypeRequest.type(), id)) {
            throw new ConflictException("Subject_type_already_exists", "Subject type already exists: " + id);
        }

        subjectType.setType(subjectTypeRequest.type());
        return subjectTypeDto(subjectTypeRepository.save(subjectType));
    }

    @Transactional
    public void deleteSubjectType(Long id) {
        if(!subjectTypeRepository.existsById(id)) {
            throw new NotFoundException("Subject_type_not_found", "Subject type not found: " + id);
        }
        subjectTypeRepository.deleteById(id);
    }
}
