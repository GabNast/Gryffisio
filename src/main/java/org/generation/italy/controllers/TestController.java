package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.TestDto;
import org.generation.italy.model.dto.TestRequest;
import org.generation.italy.services.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/{id}")
    public TestDto getById(@PathVariable Long id) {
        return testService.findById(id);
    }

    @GetMapping
    public List<TestDto> getAll() {
        return testService.findAll();
    }

    @GetMapping("/by-domain/{domainId}")
    public List<TestDto> getByDomain(@PathVariable Long domainId) {
        return testService.findByDomainId(domainId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestDto create(@Valid @RequestBody TestRequest request) {
        return testService.createTest(request);
    }

    @PutMapping("/{id}")
    public TestDto update(@PathVariable Long id, @Valid @RequestBody TestRequest request) {
        return testService.updateTest(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        testService.deleteTest(id);
    }
}