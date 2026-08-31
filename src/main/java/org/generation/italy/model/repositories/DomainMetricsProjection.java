package org.generation.italy.model.repositories;

public interface DomainMetricsProjection {
    Integer getDomainId();
    String getDomainName();
    Long getRegistrationCount();
    Long getTotalMinutes();
}
