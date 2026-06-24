package com.rohr.portfolio_api.v1.mapper;

import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;

import java.time.LocalDateTime;

public class ProjectMapper {

    public static ProjectEntity toProjectEntity(ProjectCreateForm form, Status status, Risco risco, LocalDateTime createdAt, LocalDateTime updatedAt){
        return new ProjectEntity(
                null,
                form.getNome(),
                form.getDataInicio(),
                form.getDataPrevistaTermino(),
                form.getDataTermino(),
                form.getOrcamento(),
                form.getDescricao(),
                form.getGerente(),
                status,
                risco,
                createdAt,
                updatedAt
        );
    }

    public static ProjectDTO toProjectDTO(ProjectEntity projectEntity){
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getNome(),
                projectEntity.getDataInicio(),
                projectEntity.getDataPrevistaTermino(),
                projectEntity.getDataTermino(),
                projectEntity.getOrcamento(),
                projectEntity.getDescricao(),
                projectEntity.getGerente(),
                projectEntity.getStatus(),
                projectEntity.getRisco(),
                projectEntity.getCreatedAt(),
                projectEntity.getUpdatedAt()
        );
    }
}