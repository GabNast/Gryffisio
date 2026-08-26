package org.generation.italy.model.repositories;

import org.generation.italy.dashboard.projection.OperatorDashboardProjection;
import org.generation.italy.model.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    long countByProject_Id(Long projectId);

    @Query(value = """
            select researcher.id as "operatorId",
                   concat(researcher.name, ' ', researcher.surname) as "operatorName",
                   count(registration.id) as interventions,
                   (
                       select count(*)
                       from evaluation operator_evaluation
                       join operator_registration operator_assignment
                         on operator_assignment.registration_id = operator_evaluation.registration_id
                       where operator_assignment.researcher_id = researcher.id
                   ) as evaluations,
                   coalesce(sum(registration.duration_minutes), 0) as "totalMinutes"
            from researcher
            join operator_registration
              on operator_registration.researcher_id = researcher.id
            join registration
              on registration.id = operator_registration.registration_id
            group by researcher.id, researcher.name, researcher.surname
            order by researcher.surname, researcher.name
            """, nativeQuery = true)
    List<OperatorDashboardProjection> findOperatorDashboardSummaries();
}
