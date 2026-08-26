package org.generation.italy.dashboard.projection;

/** Raw operator aggregate returned by the dashboard database query. */
public interface OperatorDashboardProjection {
    long getOperatorId();

    String getOperatorName();

    long getInterventions();

    long getEvaluations();

    long getTotalMinutes();
}
