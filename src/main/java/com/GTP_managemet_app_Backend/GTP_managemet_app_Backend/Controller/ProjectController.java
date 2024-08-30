package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Project;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // Create Project
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(
            @RequestParam String name,
            @RequestParam MultipartFile file,
            @RequestParam String createdBy) {
        try {
            Project project = projectService.createProject(name, file, createdBy);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete project
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable String projectId) {
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Edit project
    @PutMapping("/projects/{projectId}")
    public ResponseEntity<Project> editProject(
            @PathVariable String projectId,
            @RequestParam String name,
            @RequestParam(required = false) MultipartFile file) {
        try {
            Project updatedProject = projectService.editProject(projectId, name, file);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Assign project
    @PostMapping("/projects/{projectId}/assign")
    public ResponseEntity<Void> assignProject(@PathVariable String projectId, @RequestBody List<String> userIds) {
        projectService.assignProject(projectId, userIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
