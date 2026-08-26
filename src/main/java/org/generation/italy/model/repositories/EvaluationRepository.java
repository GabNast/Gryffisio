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
            select session_type.id as "sessionTypeId",
                   session_type.name as "sessionTypeName",
                   (
                       select count(*)
                       from evaluation evaluation_count
                       where evaluation_count.session_type_id = session_type.id
                   ) as evaluations,
                   coalesce((
                       select sum(registration.duration_minutes)
                       from registration
                       where exists (
                           select 1
                           from evaluation evaluation_duration
                           where evaluation_duration.registration_id = registration.id
                             and evaluation_duration.session_type_id = session_type.id
                       )
                   ), 0) as "totalMinutes"
            from session_type
            order by session_type.name
            """, nativeQuery = true)
    List<InterventionTypeDashboardProjection> findInterventionTypeDashboardSummaries();
}
