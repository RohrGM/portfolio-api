package com.rohr.portfolio_api.v1.domain.form;

import com.rohr.portfolio_api.v1.domain.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProjectUpdateForm {
    private String nome;
    private BigDecimal orcamento;
    private String descricao;
    @FutureOrPresent(message = "A DATA PREVISTA TÉRMINO deve ser uma data futura.")
    private LocalDate dataPrevistaTermino;
    private Long gerente;
    private Status status;
    @Size(max = 10, message = "A lista de MEMBROS deve ter no máximo 10 elementos.")
    private List<Long> membersIds;
}
