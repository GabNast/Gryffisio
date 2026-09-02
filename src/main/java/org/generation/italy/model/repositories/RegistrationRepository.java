package org.generation.italy.model.repositories;

import org.generation.italy.dashboard.projection.OperatorDashboardProjection;
import org.generation.italy.model.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    long countByProject_Id(Long projectId);

    @Query(value = """
            with intervention_totals as (
                select operator_registration.researcher_id,
                       count(registration.id) as interventions,
                       coalesce(sum(registration.duration_minutes), 0) as total_minutes
                from operator_registration
                join registration
                  on registration.id = operator_registration.registration_id
                group by operator_registration.researcher_id
            ), evaluation_totals as (
                select operator_registration.researcher_id,
                       count(evaluation.id) as evaluations
                from operator_registration
                join evaluation
                  on evaluation.registration_id = operator_registration.registration_id
                group by operator_registration.researcher_id
            )
            select researcher.id as "operatorId",
                   concat(researcher.name, ' ', researcher.surname) as "operatorName",
                   coalesce(intervention_totals.interventions, 0) as interventions,
                   coalesce(evaluation_totals.evaluations, 0) as evaluations,
                   coalesce(intervention_totals.total_minutes, 0) as "totalMinutes"
            from researcher
            left join intervention_totals
              on intervention_totals.researcher_id = researcher.id
            left join evaluation_totals
              on evaluation_totals.researcher_id = researcher.id
            order by researcher.surname, researcher.name
            """, nativeQuery = true)
    List<OperatorDashboardProjection> findOperatorDashboardSummaries();
}
