package org.generation.italy.dashboard.dto;

/** Dashboard counters and worked hours for one operator. */
public record OperatorSummary(
        long operatorId,
        String operatorName,
        long interventions,
        long evaluations,
        double totalHours) {
}
