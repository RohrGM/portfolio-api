package com.rohr.portfolio_api.v1.controller;

import com.rohr.portfolio_api.v1.domain.dto.ProjectDTO;
import com.rohr.portfolio_api.v1.domain.form.ProjectCreateForm;
import com.rohr.portfolio_api.v1.domain.form.ProjectFilterForm;
import com.rohr.portfolio_api.v1.domain.form.ProjectUpdateForm;
import com.rohr.portfolio_api.v1.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> createProject(
            @Valid @RequestBody ProjectCreateForm form
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.projectService.createProject(form));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectDTO>> listProject(
            @ModelAttribute ProjectFilterForm filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ) {
        return ResponseEntity.ok(this.projectService.listProjects(filter, page, size, sortBy, direction));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProjectDTO> findProjectById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(this.projectService.findProject(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectUpdateForm form
    ) {
        return ResponseEntity.ok(this.projectService.updateProject(id, form));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id
    ) {
        this.projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

}
