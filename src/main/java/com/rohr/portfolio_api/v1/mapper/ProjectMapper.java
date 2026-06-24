package com.rohr.portfolio_api.v1.mapper;

import com.rohr.portfolio_api.v1.domain.dto.MemberDTO;
import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ProjectMapper {

    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static String formatCurrency(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return currencyFormatter.format(value);
    }

    private static String formatDate(LocalDateTime value) {
        if (value == null) {
            return null;
        }
        return value.format(dateTimeFormatter);
    }

    private static String formatDate(LocalDate value) {
        if (value == null) {
            return null;
        }
        return value.format(dateFormatter);
    }

    public static ProjectEntity toProjectEntity(ProjectCreateForm form, Status status, Risco risco, LocalDateTime createdAt, LocalDateTime updatedAt){
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

    public static ProjectDTO toProjectDTO(ProjectEntity projectEntity, List<MemberDTO> members, MemberDTO manager){
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getNome(),
                formatDate(projectEntity.getDataInicio()),
                formatDate(projectEntity.getDataPrevistaTermino()),
                formatDate(projectEntity.getDataTermino()),
                formatCurrency(projectEntity.getOrcamento()),
                projectEntity.getDescricao(),
                manager,
                projectEntity.getStatus(),
                projectEntity.getRisco(),
                members,
                formatDate(projectEntity.getCreatedAt()),
                formatDate(projectEntity.getUpdatedAt())
        );
    }

    public static ProjectDTO toProjectDTO(ProjectEntity projectEntity){
        return new ProjectDTO(
                projectEntity.getId(),
                projectEntity.getNome(),
                formatDate(projectEntity.getDataInicio()),
                formatDate(projectEntity.getDataPrevistaTermino()),
                formatDate(projectEntity.getDataTermino()),
                formatCurrency(projectEntity.getOrcamento()),
                projectEntity.getDescricao(),
                projectEntity.getGerente(),
                projectEntity.getStatus(),
                projectEntity.getRisco(),
                null,
                formatDate(projectEntity.getCreatedAt()),
                formatDate(projectEntity.getUpdatedAt())
        );
    }
}