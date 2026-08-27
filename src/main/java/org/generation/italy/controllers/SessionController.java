package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.SessionDto;
import org.generation.italy.model.dto.SessionRequest;
import org.generation.italy.services.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{id}")
    public SessionDto getById(@PathVariable Integer id){
        return sessionService.findById(id);
    }

    @GetMapping
    public List<SessionDto> getAll(){
        return sessionService.findAll();
    }

    @GetMapping("/evaluationName")
    public SessionDto getByEvaluationName(@RequestParam String evaluationName){
        return sessionService.findByEvaluationName(evaluationName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionDto create(@Valid @RequestBody SessionRequest sessionRequest){
        return sessionService.createSession(sessionRequest);
    }

    @PutMapping("/{id}")
    public SessionDto update(@PathVariable Integer id, @Valid @RequestBody SessionRequest sessionRequest){
        return sessionService.updateSession(id, sessionRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        sessionService.deleteSession(id);
    }
}
