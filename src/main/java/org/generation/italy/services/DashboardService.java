package org.generation.italy.services;


import org.generation.italy.model.dto.DashboardDto;
import org.generation.italy.model.dto.ActivityMatricsDto;
import org.generation.italy.model.dto.OperatorMatricsDto;
import org.generation.italy.model.exceptions.BadRequestException;
import org.generation.italy.model.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {
    private final RegistrationRepository registrationRepository;

    public DashboardService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional(readOnly = true)
    public DashboardDto getDashboard(Integer projectId, LocalDate fromDate, LocalDate toDate) {
        validateDateRange(fromDate, toDate);

        long total = countRegistrations(projectId, fromDate, toDate);
        Long projectCount = projectId != null ? total : null;

        List<ActivityMatricsDto> activityMatrics = registrationRepository.findActivityMatricsFiltered(projectId, fromDate, toDate).stream()
                .map(row -> new ActivityMatricsDto(
                        row.getActivityId(),
                        row.getActivityName(),
                        row.getRegistrationCount(),
                        minutesToHours(row.getTotalMinutes())
                ))
                .toList();
        List<OperatorMatricsDto> operatorMatrics = registrationRepository.findOperationMatricsFiltered(projectId, fromDate, toDate).stream()
                .map(row -> new OperatorMatricsDto(
                        row.getOperatorId(),
                        row.getOperatorName(),
                        row.getRegistrationCount(),
                        minutesToHours(row.getTotalMinutes())
                ))
                .toList();
        return new DashboardDto(total,projectCount,operatorMatrics,activityMatrics);
    }

    private long countRegistrations(Integer projectId, LocalDate fromDate, LocalDate toDate) {
        if (projectId == null && fromDate == null && toDate == null) {
            return registrationRepository.count();
        }

        if (projectId != null && fromDate == null && toDate == null) {
            return registrationRepository.countByProject_Id(projectId);
        }

        if (projectId != null && fromDate != null && toDate != null) {
            return registrationRepository.countByProject_IdAndActivityDateBetween(projectId, fromDate, toDate);
        }

        if (projectId == null && fromDate != null && toDate != null) {
            return registrationRepository.countByActivityDateBetween(fromDate, toDate);
        }

        if (projectId != null && fromDate != null) {
            return registrationRepository.countByProject_IdAndActivityDateGreaterThanEqual(projectId, fromDate);
        }

        if (projectId != null) {
            return registrationRepository.countByProject_IdAndActivityDateLessThanEqual(projectId, toDate);
        }

        if (fromDate != null) {
            return registrationRepository.countByActivityDateGreaterThanEqual(fromDate);
        }

        return registrationRepository.countByActivityDateLessThanEqual(toDate);
    }

    private void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            throw new BadRequestException("INVALID_DATE_RANGE", "fromDate cannot be after toDate");
        }
    }

    public double minutesToHours (long minutes){
        return Math.round((minutes/60.0)*100.0)/100.0;
    }
}
