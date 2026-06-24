package com.rohr.portfolio_api.v1.domain.dto;

import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectDTO {
    private Long id;
    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataPrevistaTermino;
    private LocalDateTime dataTermino;
    private BigDecimal orcamento;
    private String descricao;
    private Long gerente;
    private Status status;
    private Risco risco;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
