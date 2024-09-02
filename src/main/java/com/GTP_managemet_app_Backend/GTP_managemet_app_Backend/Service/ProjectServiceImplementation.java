package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Project;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final String uploadDir = "/path/to/upload/directory"; // Define where to save uploaded files

    @Override
    public Project createProject(String name, MultipartFile file, String createdBy) throws IOException {
        // Save the file
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File fileToSave = new File(uploadDir, fileName);
        file.transferTo(fileToSave);

        // Create and save the project
        Project project = new Project();
        project.setName(name);
        project.setFilePath(fileToSave.getAbsolutePath());
        project.setCreatedBy(createdBy);
        project.setCreatedDate(Instant.now());
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(String projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project editProject(String projectId, String name, MultipartFile file) throws IOException {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setName(name);

            if (file != null && !file.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                File fileToSave = new File(uploadDir, fileName);
                file.transferTo(fileToSave);
                project.setFilePath(fileToSave.getAbsolutePath());
            }

            project.setModifiedDate(Instant.now());
            return projectRepository.save(project);
        } else {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
    }

    @Override
    public void assignProject(String projectId, List<String> userIds) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setAssignedUsers(userIds);
            projectRepository.save(project);
        } else {
            throw new IllegalArgumentException("Project not found: " + projectId);
        }
    }
}
