package com.rohr.portfolio_api.v1.domain.form;

import com.rohr.portfolio_api.v1.domain.enums.Status;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProcjectUpdateForm {
    private String nome;
    private BigDecimal orcamento;
    private String descricao;
    private Long gerente;
    private Status status;
    @Size(max = 10, message = "A lista de MEMBROS deve ter no máximo 10 elementos.")
    private List<Long> membersIds;
}
