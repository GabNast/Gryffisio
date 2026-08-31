package org.generation.italy.model.repositories;

import org.generation.italy.model.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    @Query("""
        SELECT o.id as operatorId, COUNT(r.id) as registrationCount, COALESCE(SUM(r.durationMinutes),0) as totalMinutes, CONCAT(o.fistName,' ',o.lastName) as operatorName
        
        FROM Registration r JOIN r.operators o
        GROUP BY o.id, o.firstName, o.lastName
        ORDER BY o.lastName, o.firstName  
    """)
    List<OperatorMatricsProjection> findOperationMatrics();
    @Query("""
        SELECT d.id as domainId, d.name as domainName, COUNT(r.id) as registrationCount, COALESCE(SUM(r.durationMinutes),0) as totalMinutes
    
        FROM Registration r JOIN r.domain d
        GROUP BY d.id, d.name
        ORDER BY d.name 
    """)
    List<DomainMatricsProjection> findDomainMatrics();
    long countByProject_Id (Integer projectId);
}