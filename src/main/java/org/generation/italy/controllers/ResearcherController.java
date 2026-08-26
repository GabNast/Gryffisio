package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ResearcherDto;
import org.generation.italy.model.dto.ResearcherRequest;
import org.generation.italy.services.ResearcherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/researchers")
public class ResearcherController {
    private final ResearcherService researcherService;

    public ResearcherController(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @GetMapping("/{id}")
    public ResearcherDto getResearcherById(@PathVariable Long id){
        return researcherService.findById(id);
    }

    @GetMapping
    public List<ResearcherDto> getAllResearchers(){
        return researcherService.findAllResearchers();
    }

    @GetMapping("/active")
    public List<ResearcherDto> getActiveResearchers(){
        return researcherService.findActiveResearchers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResearcherDto createResearcher(@Valid @RequestBody ResearcherRequest researcherRequest){
        return researcherService.createResearcher(researcherRequest);
    }

    @PutMapping("/{id}")
    public ResearcherDto updateResearcher(@PathVariable Long id, @Valid @RequestBody ResearcherRequest researcherRequest){
        return  researcherService.updateResearcher(id, researcherRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResearcher(@PathVariable Long id){
        researcherService.delete(id);
    }
}
