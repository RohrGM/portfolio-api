package com.rohr.portfolio_api.v1.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDTO {
    private Long id;
    private String nome;
    private String dataInicio;
    private String dataPrevistaTermino;
    private String dataTermino;
    private String orcamento;
    private String descricao;
    private Object gerente;
    private Status status;
    private Risco risco;
    private List<MemberDTO> membros;
    private String createdAt;
    private String updatedAt;
}
