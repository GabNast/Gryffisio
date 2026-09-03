package org.generation.italy.controllers;

import org.generation.italy.model.dto.DashboardDto;
import org.generation.italy.services.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    @GetMapping
    public DashboardDto getDashboard (
            @RequestParam(required = false) Integer projectId){
        return dashboardService.getDashboard(projectId);
    }
}


