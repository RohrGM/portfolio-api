package com.rohr.portfolio_api.v1.domain.entity;

import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
