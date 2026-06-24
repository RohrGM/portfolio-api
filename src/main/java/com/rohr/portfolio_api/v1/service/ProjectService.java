package com.rohr.portfolio_api.v1.service;


import com.rohr.portfolio_api.v1.client.MemberClient;
import com.rohr.portfolio_api.v1.client.response.MemberResponse;
import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.entity.ProjectMemberRelation;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.form.ProcjectUpdateForm;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;
import com.rohr.portfolio_api.v1.mapper.ProjectMapper;
import com.rohr.portfolio_api.v1.repository.ProjectMemberRelationRepository;
import com.rohr.portfolio_api.v1.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectMemberRelationRepository projectMemberRelationRepository;
    @Autowired
    private MemberClient memberClient;

    @Transactional
    public ProjectDTO createProject(ProjectCreateForm form) {

        //Valida se gerente existe
        this.checkManagerIsValid(form.getGerente());

        //Valida se membros existem e são validos
        this.checkMembersIsValid(form.getMembersIds());

        ProjectEntity projectEntity = this.projectRepository.save(
                ProjectMapper.toProjectEntity(
                        form,
                        Status.EM_ANALISE,
                        calculateRisk(form.getOrcamento(), form.getDataInicio(), form.getDataPrevistaTermino()),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                ));

        //Cria relacionamento de projeto x membro
        this.createProjectMemberRelation(projectEntity.getId(), form.getMembersIds());

        return ProjectMapper.toProjectDTO(projectEntity);
    }

    @Transactional
    public ProjectDTO updateProject(Long id, ProcjectUpdateForm form) {
        Optional<ProjectEntity> optionalProjectEntity = this.projectRepository.findById(id);

        if (optionalProjectEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");

        ProjectEntity project = optionalProjectEntity.get();

        if (!form.getNome().isBlank())
            project.setNome(form.getNome());

        if (form.getOrcamento() != null)
            project.setOrcamento(form.getOrcamento());

        if (!form.getDescricao().isBlank())
            project.setDescricao(form.getDescricao());

        if (form.getGerente() != null) {
            this.checkManagerIsValid(form.getGerente());
            project.setGerente(form.getGerente());
        }
        if (form.getStatus() != null) {
            this.checkStatusIsValid(project.getStatus(), form.getStatus());

            if(List.of(Status.ENCERRADO, Status.CANCELADO).contains(form.getStatus()))
                project.setDataTermino(LocalDateTime.now());

            project.setStatus(form.getStatus());
        }

        if (!form.getMembersIds().isEmpty()) {
            this.checkMembersIsValid(form.getMembersIds());
            this.projectMemberRelationRepository.deleteByProjectIds(id);
        }

        project.setUpdatedAt(LocalDateTime.now());

        project = this.projectRepository.save(project);

        if (!form.getMembersIds().isEmpty())
            this.createProjectMemberRelation(id, form.getMembersIds());

        return ProjectMapper.toProjectDTO(project);
    }

    @Transactional
    public void deleteProject(Long id) {
        Optional<ProjectEntity> project = this.projectRepository.findById(id);

        if (project.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Projeto não encontrado");

        if (List.of(Status.INICIADO, Status.EM_ANDAMENTO, Status.ENCERRADO).contains(project.get().getStatus()))
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Projeto não pode ser deletado, pois está iniciado, em andamento ou encerrado");

        this.projectRepository.deleteById(id);
        this.projectMemberRelationRepository.deleteByProjectIds(id);
    }

    public List<ProjectDTO> listProjects() {
        return this.projectRepository.findAll().stream()
                .map(ProjectMapper::toProjectDTO)
                .collect(Collectors.toList());
    }

    private void createProjectMemberRelation(Long projectId, List<Long> membersIds) {
        List<ProjectMemberRelation> relations = membersIds
                .stream()
                .map(memberId -> new ProjectMemberRelation(null, projectId, memberId))
                .collect(Collectors.toList());
        this.projectMemberRelationRepository.saveAll(relations);
    }

    private void checkStatusIsValid(Status current, Status newStatus) {
        if (current == Status.CANCELADO || current.ordinal() + 1 == newStatus.ordinal())
            return;

        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Sequencia de status invalida");
    }

    private void checkManagerIsValid(Long id) {
        List<MemberResponse> response = this.memberClient.findMemberByIds(List.of(id));
        if (response.isEmpty())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Gerente não encontrado");
    }

    private void checkMembersIsValid(List<Long> membersIds) {
        List<MemberResponse> memberResponses = this.memberClient.findMemberByIds(membersIds);

        if (memberResponses.size() != membersIds.size()) {

            List<Long> invalidIds = membersIds.stream()
                    .filter(memberId -> memberResponses.stream().noneMatch(resp -> resp.getId().equals(memberId)))
                    .toList();

            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Membros não encontrados: " + invalidIds
            );
        }

        List<Long> invalidJobMembers = memberResponses.stream()
                .filter(member -> !member.getCargo().equals("FUNCIONARIO"))
                .map(MemberResponse::getId)
                .toList();

        if (!invalidJobMembers.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Membros com cargo diferente de FUNCIONARIO: " + invalidJobMembers
            );

        List<Long> invalidMembers = this.projectMemberRelationRepository.findMembersWithMaxProjects(membersIds);

        if (!invalidMembers.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Membros com limite de projetos: " + invalidMembers
            );
    }

    private Risco calculateRisk(
            BigDecimal orcamento,
            LocalDateTime dataInicio,
            LocalDateTime dataPrevistaTermino
    ) {
        long months = ChronoUnit.MONTHS.between(
                dataInicio.toLocalDate(),
                dataPrevistaTermino.toLocalDate());

        if (orcamento.compareTo(new BigDecimal("500000")) > 0 || months > 6)
            return Risco.ALTO;
        if (orcamento.compareTo(new BigDecimal("100000")) <= 0 && months <= 3)
            return Risco.BAIXO;

        return Risco.MEDIO;
    }
}
