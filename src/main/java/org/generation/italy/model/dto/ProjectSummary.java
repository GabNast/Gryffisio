package org.generation.italy.model.dto;

/** Dashboard counters for a selected project. */
public record ProjectSummary(long projectId, long interventions, long evaluations) {
}
