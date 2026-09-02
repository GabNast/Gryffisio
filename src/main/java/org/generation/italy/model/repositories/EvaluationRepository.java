package org.generation.italy.model.repositories;

import org.generation.italy.dashboard.projection.InterventionTypeDashboardProjection;
import org.generation.italy.model.entities.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {

    @Query("""
            select count(evaluation)
            from Evaluation evaluation
            where evaluation.registration.project.id = :projectId
            """)
    long countByProjectId(@Param("projectId") Long projectId);

    @Query(value = """
            with evaluation_totals as (
                select evaluation.session_type_id,
                       count(evaluation.id) as evaluations
                from evaluation
                group by evaluation.session_type_id
            ), intervention_totals as (
                select typed_registration.session_type_id,
                       coalesce(sum(registration.duration_minutes), 0) as total_minutes
                from (
                    select distinct evaluation.session_type_id, evaluation.registration_id
                    from evaluation
                ) typed_registration
                join registration
                  on registration.id = typed_registration.registration_id
                group by typed_registration.session_type_id
            )
            select session_type.id as "sessionTypeId",
                   session_type.name as "sessionTypeName",
                   coalesce(evaluation_totals.evaluations, 0) as evaluations,
                   coalesce(intervention_totals.total_minutes, 0) as "totalMinutes"
            from session_type
            left join evaluation_totals
              on evaluation_totals.session_type_id = session_type.id
            left join intervention_totals
              on intervention_totals.session_type_id = session_type.id
            order by session_type.name
            """, nativeQuery = true)
    List<InterventionTypeDashboardProjection> findInterventionTypeDashboardSummaries();
}
