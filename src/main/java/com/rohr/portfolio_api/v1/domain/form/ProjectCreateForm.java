package com.rohr.portfolio_api.v1.domain.form;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ProjectCreateForm {
    @NotBlank(message = "O NOME é obrigatório.")
    @Size(min = 1, max = 100, message = "O NOME deve ter entre 1 e 100 caracteres.")
    private String nome;
    @NotNull(message = "A DATA INÍCIO é obrigatória.")
    @FutureOrPresent(message = "A DATA INÍCIO deve ser uma data presente ou futura.")
    private LocalDateTime dataInicio;
    @NotNull(message = "A DATA PREVISTA TÉRMINO é obrigatória.")
    @Future(message = "A DATA PREVISTA TÉRMINO deve ser uma data futura.")
    private LocalDateTime dataPrevistaTermino;
    @Future(message = "A DATA TÉRMINO deve ser uma data futura.")
    private LocalDateTime dataTermino;
    @NotNull(message = "O ORÇAMENTO é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O ORÇAMENTO deve ser maior que zero.")
    private BigDecimal orcamento;
    @NotBlank(message = "A DESCRIÇÃO é obrigatória.")
    private String descricao;
    @NotNull(message = "O GERENTE é obrigatório.")
    private Long gerente;
    @Size(min = 1, max = 10, message = "A lista de MEMBROS deve ter entre 1 e 10 elementos.")
    private List<Long> membersIds;
}
