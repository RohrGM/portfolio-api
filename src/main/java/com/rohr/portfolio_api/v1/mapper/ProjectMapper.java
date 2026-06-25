package com.rohr.portfolio_api.v1.mapper;

import com.rohr.portfolio_api.v1.domain.dto.MemberDTO;
import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;
import com.rohr.portfolio_api.v1.utils.DateUtils;
import com.rohr.portfolio_api.v1.utils.MonetaryUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectMapper {

    public static ProjectEntity toProjectEntity(ProjectCreateForm form, Status status, Risco risco, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new ProjectEntity(
                null,
                form.getNome(),
                form.getDataInicio(),
                form.getDataPrevistaTermino(),
                null,
                form.getOrcamento(),
                form.getDescricao(),
                form.getGerente(),
                status,
                risco,
                createdAt,
                updatedAt
        );
    }

    public static ProjectDTO toProjectDTO(ProjectEntity projectEntity, List<MemberDTO> members, MemberDTO manager) {
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getNome(),
                DateUtils.formatDate(projectEntity.getDataInicio()),
                DateUtils.formatDate(projectEntity.getDataPrevistaTermino()),
                DateUtils.formatDate(projectEntity.getDataTermino()),
                MonetaryUtils.formatCurrency(projectEntity.getOrcamento()),
                projectEntity.getDescricao(),
                manager,
                projectEntity.getStatus(),
                projectEntity.getRisco(),
                members,
                DateUtils.formatDate(projectEntity.getCreatedAt()),
                DateUtils.formatDate(projectEntity.getUpdatedAt())
        );
    }

    public static ProjectDTO toProjectDTO(ProjectEntity projectEntity) {
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getNome(),
                DateUtils.formatDate(projectEntity.getDataInicio()),
                DateUtils.formatDate(projectEntity.getDataPrevistaTermino()),
                DateUtils.formatDate(projectEntity.getDataTermino()),
                MonetaryUtils.formatCurrency(projectEntity.getOrcamento()),
                projectEntity.getDescricao(),
                projectEntity.getGerente(),
                projectEntity.getStatus(),
                projectEntity.getRisco(),
                null,
                DateUtils.formatDate(projectEntity.getCreatedAt()),
                DateUtils.formatDate(projectEntity.getUpdatedAt())
        );
    }
}