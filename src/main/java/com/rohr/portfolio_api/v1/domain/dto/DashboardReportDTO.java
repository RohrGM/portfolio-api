package com.rohr.portfolio_api.v1.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardReportDTO {
    private List<ProjectCountStatusDTO> projetosPorStatus;
    private List<ProjectCountBudgetDTO> orcamentoPorStatus;
    private Float mediaDiasPorEncerrado;
    private Long membrosAlocados;
}
