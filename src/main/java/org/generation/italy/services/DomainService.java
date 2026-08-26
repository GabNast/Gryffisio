package org.generation.italy.services;

import org.generation.italy.model.dto.DomainDto;
import org.generation.italy.model.dto.DomainRequest;
import org.generation.italy.model.entities.Domain;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomainService {
    private final DomainRepository domainRepository;

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    private DomainDto toDto(Domain domain) {
        return new DomainDto(domain.getId(), domain.getName());
    }

    @Transactional(readOnly = true)
    public List<DomainDto> findAll() {
        return domainRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DomainDto findById(Long id) {
        Domain domain = domainRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domain_not_found", "Domain not found: " + id));
        return toDto(domain);
    }

    @Transactional
    public DomainDto create(DomainRequest request) {
        if (domainRepository.existsByNameIgnoreCase(request.name())) {
            throw new ConflictException("Domain_name_unavailable", "Domain name already exists: " + request.name());
        }
        Domain domain = new Domain();
        domain.setName(request.name());
        Domain saved = domainRepository.save(domain);
        return toDto(saved);
    }

    @Transactional
    public DomainDto update(Long id, DomainRequest request) {
        Domain domain = domainRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Domain_not_found", "Domain not found: " + id));

        if (domainRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new ConflictException("Domain_name_unavailable", "Domain name already exists: " + request.name());
        }

        domain.setName(request.name());
        return toDto(domainRepository.save(domain));
    }

    @Transactional
    public void delete(Long id) {
        if (!domainRepository.existsById(id)) {
            throw new NotFoundException("Domain_not_found", "Domain not found: " + id);
        }
        domainRepository.deleteById(id);
    }

}
