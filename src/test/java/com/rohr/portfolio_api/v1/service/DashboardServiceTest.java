package com.rohr.portfolio_api.v1.service;

import com.rohr.portfolio_api.v1.domain.dto.DashboardReportDTO;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountBudgetProjection;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountStatusProjection;
import com.rohr.portfolio_api.v1.repository.ProjectMemberRelationRepository;
import com.rohr.portfolio_api.v1.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class DashboardServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRelationRepository projectMemberRelationRepository;
    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        List<ProjectCountBudgetProjection> budgetProjections = List.of(
                new ProjectCountBudgetProjection() {
                    @Override
                    public Integer getStatus() {
                        return 1;
                    }

                    @Override
                    public BigDecimal getOrcamento() {
                        return new BigDecimal("1000.0");
                    }
                },
                new ProjectCountBudgetProjection() {
                    @Override
                    public Integer getStatus() {
                        return 2;
                    }

                    @Override
                    public BigDecimal getOrcamento() {
                        return new BigDecimal("1000.0");
                    }
                }
        );

        List<ProjectCountStatusProjection> statusProjections = List.of(
                new ProjectCountStatusProjection() {
                    @Override
                    public Integer getStatus() {
                        return 1;
                    }

                    @Override
                    public int getCount() {
                        return 1;
                    }
                },
                new ProjectCountStatusProjection() {
                    @Override
                    public Integer getStatus() {
                        return 2;
                    }

                    @Override
                    public int getCount() {
                        return 1;
                    }
                }
        );

        when(this.projectRepository.countBudgetPerStatus()).thenReturn(budgetProjections);
        when(this.projectRepository.countPerStatus()).thenReturn(statusProjections);
        when(this.projectRepository.countAverageEnd()).thenReturn(10.0f);
        when(this.projectMemberRelationRepository.countAllocatedMembers()).thenReturn(5L);
    }

    @Test
    @DisplayName("Valida relatório gerado")
    void testReport() {
        DashboardReportDTO report = this.dashboardService.getReport();

        assertNotNull(report);
        assertEquals(2, report.getOrcamentoPorStatus().size());
        assertEquals(2, report.getProjetosPorStatus().size());
        assertEquals(10.0f, report.getMediaDiasPorEncerrado());
        assertEquals(5L, report.getMembrosAlocados());
    }

}