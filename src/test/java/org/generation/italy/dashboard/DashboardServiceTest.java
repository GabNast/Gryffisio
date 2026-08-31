/*package org.generation.italy.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.generation.italy.dashboard.dto.DashboardData;
import org.generation.italy.dashboard.dto.InterventionTypeSummary;
import org.generation.italy.dashboard.dto.OperatorMatricsDto;
import org.generation.italy.dashboard.projection.DomainMetricsProjection;
import org.generation.italy.dashboard.projection.OperatorMatricsProjection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

class DashboardServiceTest {

    private RegistrationRepository registrationRepository;
    private EvaluationRepository evaluationRepository;
    private DashboardService dashboardService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        registrationRepository = mock(RegistrationRepository.class);
        evaluationRepository = mock(EvaluationRepository.class);
        dashboardService = new DashboardService(registrationRepository, evaluationRepository);

        when(registrationRepository.findOperatorDashboardSummaries()).thenReturn(List.of(
                new OperatorProjection(1L, "Mario Rossi", 4L, 6L, 150L),
                new OperatorProjection(2L, "Giulia Bianchi", 2L, 3L, 45L)
        ));
        when(evaluationRepository.findInterventionTypeDashboardSummaries()).thenReturn(List.of(
                new InterventionTypeProjection(1L, "Assessment", 5L, 180L),
                new InterventionTypeProjection(2L, "Follow-up", 4L, 30L)
        ));
    }

    @AfterEach
    void tearDown() {
        reset(registrationRepository, evaluationRepository);
    }

    @ParameterizedTest
    @MethodSource("dashboardTotals")
    void getDashboardData_displaysExpectedTotalsAndHours(
            long interventions, long evaluations, double expectedFirstOperatorHours) throws JsonProcessingException {
        when(registrationRepository.count()).thenReturn(interventions);
        when(evaluationRepository.count()).thenReturn(evaluations);

        DashboardData result = dashboardService.getDashboardData();

        assertEquals(interventions, result.totalInterventions());
        assertEquals(evaluations, result.totalEvaluations());
        assertNull(result.selectedProject());

        OperatorMatricsDto firstOperator = result.operators().getFirst();
        assertEquals("Mario Rossi", firstOperator.operatorName());
        assertEquals(4L, firstOperator.interventions());
        assertEquals(6L, firstOperator.evaluations());
        assertEquals(expectedFirstOperatorHours, firstOperator.totalHours());

        InterventionTypeSummary firstType = result.interventionTypes().getFirst();
        assertEquals("Assessment", firstType.sessionTypeName());
        assertEquals(5L, firstType.evaluations());
        assertEquals(3.0, firstType.totalHours());

        printFrontendPayload(result);
    }

    @ParameterizedTest
    @MethodSource("selectedProjectTotals")
    void getDashboardData_withProject_displaysProjectTotals(
            long projectId, long interventions, long evaluations) throws JsonProcessingException {
        when(registrationRepository.count()).thenReturn(10L);
        when(evaluationRepository.count()).thenReturn(20L);
        when(registrationRepository.countByProject_Id(projectId)).thenReturn(interventions);
        when(evaluationRepository.countByProjectId(projectId)).thenReturn(evaluations);

        DashboardData result = dashboardService.getDashboardData(projectId);

        assertEquals(projectId, result.selectedProject().projectId());
        assertEquals(interventions, result.selectedProject().interventions());
        assertEquals(evaluations, result.selectedProject().evaluations());

        printFrontendPayload(result);
    }

    private static Stream<Arguments> dashboardTotals() {
        return Stream.of(
                Arguments.of(6L, 9L, 2.5),
                Arguments.of(0L, 0L, 2.5)
        );
    }

    private static Stream<Arguments> selectedProjectTotals() {
        return Stream.of(
                Arguments.of(1L, 4L, 6L),
                Arguments.of(7L, 0L, 0L)
        );
    }

    private void printFrontendPayload(DashboardData dashboardData) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(dashboardData));
    }

    private record OperatorProjection(
            long operatorId,
            String operatorName,
            long interventions,
            long evaluations,
            long totalMinutes) implements OperatorMatricsProjection {

        @Override
        public long getOperatorId() {
            return operatorId;
        }

        @Override
        public String getOperatorName() {
            return operatorName;
        }

        @Override
        public long getInterventions() {
            return interventions;
        }

        @Override
        public long getEvaluations() {
            return evaluations;
        }

        @Override
        public long getTotalMinutes() {
            return totalMinutes;
        }
    }

    private record InterventionTypeProjection(
            long sessionTypeId,
            String sessionTypeName,
            long evaluations,
            long totalMinutes) implements DomainMetricsProjection {

        @Override
        public long getSessionTypeId() {
            return sessionTypeId;
        }

        @Override
        public String getSessionTypeName() {
            return sessionTypeName;
        }

        @Override
        public long getEvaluations() {
            return evaluations;
        }

        @Override
        public long getTotalMinutes() {
            return totalMinutes;
        }
    }
}

 */
