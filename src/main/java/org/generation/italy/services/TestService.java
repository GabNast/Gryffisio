package org.generation.italy.services;

import org.generation.italy.model.dto.TestDto;
import org.generation.italy.model.dto.TestRequest;
import org.generation.italy.model.entities.Domain;
import org.generation.italy.model.entities.Test;
import org.generation.italy.model.exceptions.ConflictException;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.model.repositories.DomainRepository;
import org.generation.italy.model.repositories.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final DomainRepository domainRepository;

    public TestService(TestRepository testRepository, DomainRepository domainRepository) {
        this.testRepository = testRepository;
        this.domainRepository = domainRepository;
    }

    private TestDto toDto(Test test) {
        return new TestDto(
                test.getId(),
                test.getName(),
                test.getDomains().stream().map(Domain::getId).toList(),
                test.getDomains().stream().map(Domain::getName).toList()
        );
    }

    @Transactional(readOnly = true)
    public List<TestDto> findAll() {
        return testRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TestDto findById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test_not_found", "Test not found: " + id));
        return toDto(test);
    }

    @Transactional(readOnly = true)
    public List<TestDto> findByDomainId(Long domainId) {
        return testRepository.findByDomains_Id(domainId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public TestDto createTest(TestRequest request) {
        if (testRepository.existsByNameIgnoreCase(request.name())) {
            throw new ConflictException("Test_name_already_exists", "Test name already exists: " + request.name());
        }
        Test test = new Test();
        test.setName(request.name());
        test.setDomains(resolveDomains(request.domainIds()));
        Test saved = testRepository.save(test);
        return toDto(saved);
    }

    @Transactional
    public TestDto updateTest(Long id, TestRequest request) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Test_not_found", "Test not found: " + id));

        if (testRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new ConflictException("Test_name_already_exists", "Test name already exists: " + request.name());
        }

        test.setName(request.name());
        test.setDomains(resolveDomains(request.domainIds()));
        return toDto(testRepository.save(test));
    }

    private Set<Domain> resolveDomains(List<Long> domainIds) {
        if (domainIds == null || domainIds.isEmpty()) {
            return Set.of();
        }
        Set<Domain> domains = Set.copyOf(domainRepository.findAllById(domainIds));
        if (domains.size() != Set.copyOf(domainIds).size()) {
            throw new NotFoundException("Domain_not_found", "One or more domain ids do not exist");
        }
        return domains;
    }

    @Transactional
    public void deleteTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new NotFoundException("Test_not_found", "Test not found: " + id);
        }
        testRepository.deleteById(id);
    }
}