package org.generation.italy.model.repositories;

public interface OperatorMatricsProjection {
    Integer getOperatorId();
    String getOperatorName();
    Long getTotalMinutes();
    Long getRegistrationCount();
}
