package org.generation.italy.dashboard.dto;

import java.util.List;

/** Complete payload returned by the administrator dashboard. */
public record DashboardData(
        long totalInterventions,
        long totalEvaluations,
        ProjectSummary selectedProject,
        List<OperatorSummary> operators,
        List<InterventionTypeSummary> interventionTypes) {
}
