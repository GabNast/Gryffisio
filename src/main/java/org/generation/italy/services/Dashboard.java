package org.generation.italy.services;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Provides the aggregate data displayed in the administrator dashboard. */
@Service
public class Dashboard {
    private final EntityManager entityManager;

    public Dashboard(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Returns all dashboard data. When {@code projectId} is supplied, the
     * project-specific counters are populated as well.
     */
    @Transactional(readOnly = true)
    public DashboardData getDashboardData(Long projectId) {
        return new DashboardData(
                count("select count(*) from registration"),
                count("select count(*) from evaluation"),
                projectId == null ? null : projectSummary(projectId),
                operatorSummaries(),
                interventionTypeSummaries()
        );
    }

    /** Convenience overload for a dashboard without a selected project. */
    @Transactional(readOnly = true)
    public DashboardData getDashboardData() {
        return getDashboardData(null);
    }

    private ProjectSummary projectSummary(Long projectId) {
        return new ProjectSummary(
                projectId,
                count("select count(*) from registration where project_id = :projectId", projectId),
                count("""
                        select count(*)
                        from evaluation e
                        join registration r on r.id = e.registration_id
                        where r.project_id = :projectId
                        """, projectId)
        );
    }

    @SuppressWarnings("unchecked")
    private List<OperatorSummary> operatorSummaries() {
        List<Object[]> rows = entityManager.createNativeQuery("""
                select researcher.id,
                       researcher.name,
                       researcher.surname,
                       count(registration.id),
                       (
                           select count(*)
                           from evaluation operator_evaluation
                           join operator_registration operator_assignment
                             on operator_assignment.registration_id = operator_evaluation.registration_id
                           where operator_assignment.researcher_id = researcher.id
                       ),
                       coalesce(sum(registration.duration_minutes), 0)
                from researcher
                join operator_registration operator_registration
                  on operator_registration.researcher_id = researcher.id
                join registration on registration.id = operator_registration.registration_id
                group by researcher.id, researcher.name, researcher.surname
                order by researcher.surname, researcher.name
                """).getResultList();

        return rows.stream()
                .map(row -> new OperatorSummary(
                        ((Number) row[0]).longValue(),
                        row[1] + " " + row[2],
                        ((Number) row[3]).longValue(),
                        ((Number) row[4]).longValue(),
                        minutesToHours(((Number) row[5]).longValue())
                ))
                .toList();
    }

    @SuppressWarnings("unchecked")
    private List<InterventionTypeSummary> interventionTypeSummaries() {
        List<Object[]> rows = entityManager.createNativeQuery("""
                select session_type.id,
                       session_type.name,
                       count(evaluation.id),
                       coalesce(sum(registration.duration_minutes), 0)
                from session_type
                left join evaluation on evaluation.session_type_id = session_type.id
                left join registration on registration.id = evaluation.registration_id
                group by session_type.id, session_type.name
                order by session_type.name
                """).getResultList();

        return rows.stream()
                .map(row -> new InterventionTypeSummary(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        ((Number) row[2]).longValue(),
                        minutesToHours(((Number) row[3]).longValue())
                ))
                .toList();
    }

    private long count(String sql) {
        return ((Number) entityManager.createNativeQuery(sql).getSingleResult()).longValue();
    }

    private long count(String sql, Long projectId) {
        return ((Number) entityManager.createNativeQuery(sql)
                .setParameter("projectId", projectId)
                .getSingleResult()).longValue();
    }

    private double minutesToHours(long minutes) {
        return Math.round((minutes / 60.0) * 100.0) / 100.0;
    }

    public record DashboardData(
            long totalInterventions,
            long totalEvaluations,
            ProjectSummary selectedProject,
            List<OperatorSummary> operators,
            List<InterventionTypeSummary> interventionTypes) {
    }

    public record ProjectSummary(long projectId, long interventions, long evaluations) {
    }

    public record OperatorSummary(
            long operatorId,
            String operatorName,
            long interventions,
            long evaluations,
            double totalHours) {
    }

    public record InterventionTypeSummary(
            long sessionTypeId,
            String sessionTypeName,
            long evaluations,
            double totalHours) {
    }
}
