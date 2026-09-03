package org.generation.italy.model.repositories;

public interface OperatorMetricsProjection {
    Integer getOperatorId();
    String getOperatorName();
    Long getTotalMinutes();
    Long getRegistrationCount();
}
