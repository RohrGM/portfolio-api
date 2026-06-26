package com.rohr.portfolio_api.v1.domain.form;

import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFilterForm {
    private Long id;
    private String nome;
    private Status status;
    private Risco risco;
}
