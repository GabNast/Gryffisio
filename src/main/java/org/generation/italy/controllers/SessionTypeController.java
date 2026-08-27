package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SessionTypeDto;
import org.generation.italy.model.dto.SessionTypeRequest;
import org.generation.italy.services.SessionService;
import org.generation.italy.services.SessionTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session-types")
public class SessionTypeController {
    private final SessionTypeService sessionTypeService;
    public SessionTypeController(SessionTypeService sessionTypeService) {
        this.sessionTypeService = sessionTypeService;
    }

    @GetMapping("/{id}")
    public SessionTypeDto getById(@PathVariable Integer id){
        return sessionTypeService.findById(id);
    }

    @GetMapping
    public List<SessionTypeDto> getAllSessionTypes(){
        return sessionTypeService.findAll();
    }

    @GetMapping("/code")
    public SessionTypeDto getCode(@RequestParam String code){
        return sessionTypeService.findByCodeIgnoreCase(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionTypeDto createSessionType(@Valid @RequestBody SessionTypeRequest sessionTypeRequest){
        return sessionTypeService.createSessionType(sessionTypeRequest);
    }

    @PutMapping("/{id}")
    public SessionTypeDto updateSessionType(@PathVariable Integer id, @Valid @RequestBody SessionTypeRequest sessionTypeRequest){
        return sessionTypeService.updateSessionType(id, sessionTypeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteSessionType(@PathVariable Integer id){
        sessionTypeService.deleteSessionType(id);
    }

}
