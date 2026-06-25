package com.rohr.portfolio_api.v1.service;

import com.rohr.portfolio_api.v1.client.MemberClient;
import com.rohr.portfolio_api.v1.client.response.MemberResponse;
import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.entity.ProjectEntity;
import com.rohr.portfolio_api.v1.domain.enums.Risco;
import com.rohr.portfolio_api.v1.domain.enums.Status;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;
import com.rohr.portfolio_api.v1.domain.form.ProjectUpdateForm;
import com.rohr.portfolio_api.v1.repository.ProjectMemberRelationRepository;
import com.rohr.portfolio_api.v1.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class ProjectServiceTest {

    private final Map<Long, MemberResponse> memberMock = Map.ofEntries(
            Map.entry(1L, new MemberResponse(1L, "Membro 1", "FUNCIONARIO")),
            Map.entry(2L, new MemberResponse(2L, "Membro 2", "FUNCIONARIO")),
            Map.entry(3L, new MemberResponse(3L, "Membro 3", "FUNCIONARIO")),
            Map.entry(4L, new MemberResponse(4L, "Membro 4", "FUNCIONARIO")),
            Map.entry(5L, new MemberResponse(5L, "Membro 5", "FUNCIONARIO")),
            Map.entry(6L, new MemberResponse(6L, "Membro 6", "FUNCIONARIO")),
            Map.entry(7L, new MemberResponse(7L, "Membro 7", "FUNCIONARIO")),
            Map.entry(8L, new MemberResponse(8L, "Membro 8", "FUNCIONARIO")),
            Map.entry(9L, new MemberResponse(9L, "Membro 9", "FUNCIONARIO")),
            Map.entry(10L, new MemberResponse(10L, "Membro 10", "FUNCIONARIO")),
            Map.entry(11L, new MemberResponse(11L, "Membro 11", "FUNCIONARIO")),
            Map.entry(12L, new MemberResponse(12L, "Membro 12", "OUTROS"))
    );
    private final Map<Long, ProjectEntity> projectMock = Map.of(
            1L, new ProjectEntity(
                    1L,
                    "teste",
                    LocalDate.now(),
                    LocalDate.now().plusMonths(1),
                    null,
                    new BigDecimal("600000.00"),
                    "descrição de teste",
                    1L,
                    Status.EM_ANALISE,
                    Risco.ALTO,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            ),
            2L, new ProjectEntity(
                    2L,
                    "teste2",
                    LocalDate.now(),
                    LocalDate.now().plusMonths(1),
                    null,
                    new BigDecimal("1000.00"),
                    "descrição de teste2",
                    1L,
                    Status.INICIADO,
                    Risco.BAIXO,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            )
    );
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMemberRelationRepository projectMemberRelationRepository;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(this.memberService.findMemberByIds(List.of(1L)))
                .thenReturn(List.of(memberMock.get(1L)));
        when(this.memberService.findMemberByIds(List.of(2L, 3L)))
                .thenReturn(List.of(memberMock.get(2L), memberMock.get(3L)));
        when(this.projectRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(this.projectRepository.findById(1L))
                .thenReturn(Optional.of(projectMock.get(1L)));
        when(this.projectRepository.findById(2L))
                .thenReturn(Optional.of(projectMock.get(2L)));
    }

    //======================++++++++==============CRIAÇÃO DE PROJETO====================================================
    @Test
    @DisplayName("Cria projeto valido")
    void createProjectValid() {
        ProjectDTO project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusDays(30), new BigDecimal("1000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));

        //Projeto criado
        assertNotNull(project);
    }

    @Test
    @DisplayName("Cria projeto no estado inicial correto")
    void createProjectValidStatus() {
        ProjectDTO project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusDays(30), new BigDecimal("1000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));

        assertEquals(Status.EM_ANALISE, project.getStatus());
    }

    @Test
    @DisplayName("Cria projeto e valida calculo de risco baixo")
    void createProjectValidRiskLow() {
        ProjectDTO project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusDays(30), new BigDecimal("1000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));

        assertEquals(Risco.BAIXO, project.getRisco());
    }

    @Test
    @DisplayName("Cria projeto e valida calculo de risco medio")
    void createProjectValidRiskMedium() {
        ProjectDTO project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusDays(30), new BigDecimal("200000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));
        assertEquals(Risco.MEDIO, project.getRisco());

        project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusMonths(4), new BigDecimal("1000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));
        assertEquals(Risco.MEDIO, project.getRisco());
    }

    @Test
    @DisplayName("Cria projeto e valida calculo de risco alto")
    void createProjectValidRiskHigh() {
        ProjectDTO project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusDays(30), new BigDecimal("600000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));
        assertEquals(Risco.ALTO, project.getRisco());

        project = this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                LocalDate.now().plusMonths(7), new BigDecimal("1000.00"),
                "descrição de teste", 1L, List.of(2L, 3L)));
        assertEquals(Risco.ALTO, project.getRisco());
    }

    @Test
    @DisplayName("Cria projeto e valida exceção de quantidade de membros alocados vazio")
    void createProjectEmptyMembers() {
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                        LocalDate.now().plusDays(30), new BigDecimal("600000.00"),
                        "descrição de teste", 1L, List.of()))
        );
    }

    @Test
    @DisplayName("Cria projeto e valida exceção de quantidade de membros alocados acima do limite")
    void createProjectOverMembers() {
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                        LocalDate.now().plusDays(30), new BigDecimal("600000.00"),
                        "descrição de teste", 1L, List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L)))
        );
    }

    @Test
    @DisplayName("Cria projeto e valida exceção por membro invalido")
    void createProjectInvalidMember() {
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                        LocalDate.now().plusDays(30), new BigDecimal("600000.00"),
                        "descrição de teste", 1L, List.of(12L)))
        );
    }

    @Test
    @DisplayName("Cria projeto e valida exceção por membros ja alocados em outros projetos")
    void createProjectInvalidMembers() {
        when(this.projectMemberRelationRepository.findMembersWithMaxProjects(any()))
                .thenReturn(List.of(1L));
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.createProject(new ProjectCreateForm("teste", LocalDate.now(),
                        LocalDate.now().plusDays(30), new BigDecimal("600000.00"),
                        "descrição de teste", 1L, List.of(1L)))
        );
    }

    //======================++++++++============ATUALIZAÇÃO DE PROJETO==================================================

    @Test
    @DisplayName("Atualiza projeto não invalido")
    void updateProject() {
        when(this.projectRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.updateProject(10L, new ProjectUpdateForm()));
    }

    @Test
    @DisplayName("Atualiza projeto novo status valido")
    void updateValidStatus() {
        ProjectDTO project = this.projectService.updateProject(1L, new ProjectUpdateForm(null, null, null, null, null, Status.ANALISE_REALIZADA, null));
        assertEquals(Status.ANALISE_REALIZADA, project.getStatus());
    }

    @Test
    @DisplayName("Atualiza projecto com novo status invalido")
    void updateInvalidStatus() {
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.updateProject(1L, new ProjectUpdateForm(null, null, null, null, null, Status.EM_ANDAMENTO, null)));
    }

    @Test
    @DisplayName("Atualiza projecto com cancelamento")
    void updateCancelation() {
        ProjectDTO project = this.projectService.updateProject(1L, new ProjectUpdateForm(null, null, null, null, null, Status.CANCELADO, null));
        assertEquals(Status.CANCELADO, project.getStatus());
    }

    //==============================================DELEÇÃO DE PROJETO==================================================

    @Test
    @DisplayName("Deleta projeto não existente")
    void deleteProjectNotFound() {
        when(this.projectRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.deleteProject(10L));
    }

    @Test
    @DisplayName("Deleta projeto valido")
    void deleteProjectValid() {
        assertDoesNotThrow(() -> this.projectService.deleteProject(1L));
    }

    @Test
    @DisplayName("Deleta projeto com status invalido")
    void deleteProjectInvalidStatus() {
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.deleteProject(2L));
    }

    //=============================================CONSULTA DE PROJETO==================================================

    @Test
    @DisplayName("Encontra projeto por ID")
    void findProject() {
        ProjectDTO project = this.projectService.findProject(1L);
        assertNotNull(project);
    }

    @Test
    @DisplayName("Encontra projeto por ID não existente")
    void findProjectNotFound() {
        when(this.projectRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                ResponseStatusException.class,
                () -> this.projectService.findProject(1L));
    }
}