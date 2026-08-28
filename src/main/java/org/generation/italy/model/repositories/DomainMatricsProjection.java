package org.generation.italy.model.repositories;

public interface DomainMatricsProjection {
    Integer getDomainId();
    String getDomainName();
    Long getRegistrationCount();
    Long getTotalMinutes();
}
