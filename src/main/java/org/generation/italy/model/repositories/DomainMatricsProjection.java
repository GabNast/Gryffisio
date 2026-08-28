package org.generation.italy.model.repositories;

public interface DomainMatricsProjection {
    Integer getDomainId();
    Long getDomainName();
    Long getRegistrationCount();
    Long getTotalMinutes();
}
