package org.generation.italy.controllers;

import jakarta.validation.Valid;
import org.generation.italy.model.dto.ModificationRequestDecision;
import org.generation.italy.model.dto.ModificationRequestDto;
import org.generation.italy.model.dto.ModificationRequestRequest;
import org.generation.italy.model.exceptions.NotFoundException;
import org.generation.italy.services.ModificationRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modification-requests")
public class ModificationRequestController {
    private final ModificationRequestService modificationRequestService;

    public ModificationRequestController(ModificationRequestService modificationRequestService) {
        this.modificationRequestService = modificationRequestService;
    }

    @GetMapping("/{id}")
    public ModificationRequestDto getById(@PathVariable Long id) throws NotFoundException {
        return modificationRequestService.findById(id);
    }

    @GetMapping
    public List<ModificationRequestDto> getAll(@RequestParam(required = false) String status) {
        return modificationRequestService.findAll(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModificationRequestDto create(@Valid @RequestBody ModificationRequestRequest request) throws NotFoundException {
        return modificationRequestService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModificationRequestDto decide(
            @PathVariable Long id,
            @Valid @RequestBody ModificationRequestDecision decision,
            @AuthenticationPrincipal Jwt jwt
    ) throws NotFoundException {
        Number uid = jwt.getClaim("uid");
        Integer adminId = uid.intValue();
        return modificationRequestService.decide(id, adminId, decision);
    }
}