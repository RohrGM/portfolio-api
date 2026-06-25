package com.rohr.portfolio_api.v1.service;

import com.rohr.portfolio_api.v1.domain.dto.DashboardReportDTO;
import com.rohr.portfolio_api.v1.mapper.DashboardMapper;
import com.rohr.portfolio_api.v1.repository.ProjectMemberRelationRepository;
import com.rohr.portfolio_api.v1.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMemberRelationRepository projectMemberRelationRepository;

    public DashboardReportDTO getReport() {
        return new DashboardReportDTO(
                this.projectRepository.countPerStatus().stream()
                        .map(DashboardMapper::toCountStatusDTO)
                        .collect(Collectors.toList()),
                this.projectRepository.countBudgetPerStatus().stream()
                        .map(DashboardMapper::toCountBudgetDTO)
                        .collect(Collectors.toList()),
                this.projectRepository.countAverageEnd(),
                this.projectMemberRelationRepository.countAllocatedMembers()
        );
    }
}
