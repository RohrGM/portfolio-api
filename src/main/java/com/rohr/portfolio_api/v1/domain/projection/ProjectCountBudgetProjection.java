package com.rohr.portfolio_api.v1.domain.projection;

import java.math.BigDecimal;

public interface ProjectCountBudgetProjection {
    Integer getStatus();
    BigDecimal getOrcamento();
}
