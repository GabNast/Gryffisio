package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ReferringDoctorDto;
import org.generation.italy.model.dto.ReferringDoctorRequest;
import org.generation.italy.services.ReferringDoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referring-doctors")
public class ReferringDoctorController {
    private final ReferringDoctorService referringDoctorService;

    public ReferringDoctorController(ReferringDoctorService referringDoctorService) {
        this.referringDoctorService = referringDoctorService;
    }

    @GetMapping("/{id}")
    public ReferringDoctorDto getReferringDoctorById(@PathVariable Long id){
        return referringDoctorService.findById(id);
    }

    @GetMapping
    public List<ReferringDoctorDto> getReferringDoctors(){
        return referringDoctorService.findAll();
    }

    @GetMapping("/searchByName")
    public ReferringDoctorDto getReferringDoctorsByNameAndSurname(@RequestParam String name, @RequestParam String surname){
        return referringDoctorService.findByNameAndSurname(name, surname);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReferringDoctorDto createReferringDoctor(@Valid @RequestBody ReferringDoctorRequest referringDoctorRequest){
        return referringDoctorService.createReferringDoctor(referringDoctorRequest);
    }

    @PutMapping("/{id}")
    public ReferringDoctorDto updateReferringDoctor(@PathVariable Long id,  @Valid @RequestBody ReferringDoctorRequest referringDoctorRequest){
        return referringDoctorService.updateReferringDoctor(id, referringDoctorRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReferringDoctor(@PathVariable Long id){
        referringDoctorService.deleteById(id);
    }

}
