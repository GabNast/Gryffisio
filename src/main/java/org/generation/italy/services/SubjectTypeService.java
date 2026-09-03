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

    private SubjectTypeDto toDto(SubjectType subjectType) {
        return new SubjectTypeDto(subjectType.getId(), subjectType.getType());
    }

    @Transactional(readOnly = true)
    public List<SubjectTypeDto> findAll() {
        return subjectTypeRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubjectTypeDto findById(Integer id) {
        SubjectType subjectType = subjectTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + id));
        return toDto(subjectType);
    }

    @Transactional
    public SubjectTypeDto createSubjectType(SubjectTypeRequest request) {
        if (subjectTypeRepository.existsByTypeIgnoreCase(request.type())) {
            throw new ConflictException("Subject_type_already_exists", "Subject type already exists: " + request.type());
        }
        SubjectType subjectType = new SubjectType();
        subjectType.setType(request.type());
        SubjectType saved = subjectTypeRepository.save(subjectType);
        return toDto(saved);
    }

    @Transactional
    public SubjectTypeDto updateSubjectType(Integer id, SubjectTypeRequest request) {
        SubjectType subjectType = subjectTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject_type_not_found", "Subject type not found: " + id));

        if (subjectTypeRepository.existsByTypeIgnoreCaseAndIdNot(request.type(), id)) {
            throw new ConflictException("Subject_type_already_exists", "Subject type already exists: " + request.type());
        }

        subjectType.setType(request.type());
        return toDto(subjectTypeRepository.save(subjectType));
    }

    @Transactional
    public void deleteSubjectType(Integer id) {
        if (!subjectTypeRepository.existsById(id)) {
            throw new NotFoundException("Subject_type_not_found", "Subject type not found: " + id);
        }
        subjectTypeRepository.deleteById(id);
    }
}