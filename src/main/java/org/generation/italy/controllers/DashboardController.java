package org.generation.italy.controllers;

import org.generation.italy.model.dto.DashboardData;
import org.generation.italy.services.Dashboard;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    private final Dashboard dashboard;

    public DashboardController(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * Returns all administrator dashboard metrics.
     *
     * @param projectId optional selected project identifier
     */
    @GetMapping
    public DashboardData getDashboardData(
            @RequestParam(required = false) Long projectId) {
        return dashboard.getDashboardData(projectId);
    }
}
