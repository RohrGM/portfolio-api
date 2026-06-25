package com.rohr.portfolio_api.v1.mapper;

import com.rohr.portfolio_api.v1.domain.dto.ProjectCountBudgetDTO;
import com.rohr.portfolio_api.v1.domain.dto.ProjectCountStatusDTO;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountBudgetProjection;
import com.rohr.portfolio_api.v1.domain.projection.ProjectCountStatusProjection;
import com.rohr.portfolio_api.v1.utils.MonetaryUtils;

public class DashboardMapper {

    public static ProjectCountBudgetDTO toCountBudgetDTO(ProjectCountBudgetProjection projection) {
        return new ProjectCountBudgetDTO(
                Status.values()[projection.getStatus()],
                MonetaryUtils.formatCurrency(projection.getOrcamento())
        );
    }

    public static ProjectCountStatusDTO toCountStatusDTO(ProjectCountStatusProjection projection) {
        return new ProjectCountStatusDTO(
                Status.values()[projection.getStatus()],
                projection.getCount()
        );
    }
}
