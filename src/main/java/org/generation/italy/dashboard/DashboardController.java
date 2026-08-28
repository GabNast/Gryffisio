/*
package org.generation.italy.dashboard;

import org.generation.italy.dashboard.dto.DashboardData;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    */
/**
     * Returns all administrator dashboard metrics.
     *
     * @param projectId optional selected project identifier
     *//*

    @GetMapping
    public DashboardData getDashboardData(
            @RequestParam(required = false) Long projectId) {
        return dashboardService.getDashboardData(projectId);
    }
}
*/
