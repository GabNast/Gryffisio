package org.generation.italy.dashboard;

import org.generation.italy.dashboard.dto.DashboardData;
import org.generation.italy.dashboard.dto.InterventionTypeSummary;
import org.generation.italy.dashboard.dto.OperatorSummary;
import org.generation.italy.dashboard.dto.ProjectSummary;
import org.generation.italy.model.repositories.EvaluationRepository;
import org.generation.italy.model.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Provides the aggregate data displayed in the administrator dashboard. */
@Service
public class DashboardService {
    private final RegistrationRepository registrationRepository;
    private final EvaluationRepository evaluationRepository;

    public DashboardService(RegistrationRepository registrationRepository,
                            EvaluationRepository evaluationRepository) {
        this.registrationRepository = registrationRepository;
        this.evaluationRepository = evaluationRepository;
    }

    /**
     * Returns all dashboard data. When {@code projectId} is supplied, the
     * project-specific counters are populated as well.
     */
    @Transactional(readOnly = true)
    public DashboardData getDashboardData(Long projectId) {
        return new DashboardData(
                registrationRepository.count(),
                evaluationRepository.count(),
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
                registrationRepository.countByProject_Id(projectId),
                evaluationRepository.countByProjectId(projectId)
        );
    }
    //La query restituisce una lista "raw" perchè jpa non conosce il tipo di ritorno,
    //siccome ogni riga ha più colonne sarà rappresentata come un array di oggetti (Object[]),
    //quindi la lista sarà List<Object[]> ma java ci segnalerà un warning di tipo "unchecked" perchè non può garantire
    // il tipo di ritorno, quindi lo sopprimiamo con @SuppressWarnings("unchecked"),
    //il codice funzionerebbe lo stesso senza l'annotazione ma è buona pratica sopprimere i warning
    // quando sappiamo che il codice è corretto.
    private List<OperatorSummary> operatorSummaries() {
        return registrationRepository.findOperatorDashboardSummaries().stream()
                .map(row -> new OperatorSummary(
                        row.getOperatorId(),
                        row.getOperatorName(),
                        row.getInterventions(),
                        row.getEvaluations(),
                        minutesToHours(row.getTotalMinutes())
                ))
                .toList();
    }

    private List<InterventionTypeSummary> interventionTypeSummaries() {
        return evaluationRepository.findInterventionTypeDashboardSummaries().stream()
                .map(row -> new InterventionTypeSummary(
                        row.getSessionTypeId(),
                        row.getSessionTypeName(),
                        row.getEvaluations(),
                        minutesToHours(row.getTotalMinutes())
                ))
                .toList();
    }

    private double minutesToHours(long minutes) {
        return Math.round((minutes / 60.0) * 100.0) / 100.0;
    }

}
