package org.generation.italy.dashboard.projection;

/** Raw intervention-type aggregate returned by the dashboard database query. */
public interface InterventionTypeDashboardProjection {
    long getSessionTypeId();

    String getSessionTypeName();

    long getEvaluations();

    long getTotalMinutes();
}
