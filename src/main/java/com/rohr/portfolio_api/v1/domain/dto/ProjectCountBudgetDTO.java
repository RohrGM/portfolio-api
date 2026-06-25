package com.rohr.portfolio_api.v1.domain.dto;

import com.rohr.portfolio_api.v1.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectCountBudgetDTO {
    private Status status;
    private String orcamento;
}
