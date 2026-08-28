package org.generation.italy.services;


import org.generation.italy.model.dto.DashboardDto;
import org.generation.italy.model.dto.DomainMatricsDto;
import org.generation.italy.model.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardService {
    public final RegistrationRepository registrationRepository;

    public DashboardService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Transactional(readOnly = true)
    public DashboardDto getDashboard(Integer projectId){
        long total = registrationRepository.count();
        Long projectCount = projectId !=null ? registrationRepository.countByProject_Id(projectId):null;
        List<DomainMatricsDto> domainMatrics =
}
