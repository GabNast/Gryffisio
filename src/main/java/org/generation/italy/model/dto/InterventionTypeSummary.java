package org.generation.italy.model.dto;

/** Dashboard counters and worked hours for one intervention/evaluation type. */
public record InterventionTypeSummary(
        long sessionTypeId,
        String sessionTypeName,
        long evaluations,
        double totalHours) {
}
