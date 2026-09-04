package org.generation.italy.model.repositories;

public interface ActivityMetricsProjection {
    Integer getActivityId();
    String getActivityName();
    Long getRegistrationCount();
    Long getTotalMinutes();
}
