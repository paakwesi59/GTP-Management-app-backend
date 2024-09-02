package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Project;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    Project createProject(String name, MultipartFile file, String createdBy) throws IOException;
    void deleteProject(String projectId);
    Project editProject(String projectId, String name, MultipartFile file) throws IOException;
    void assignProject(String projectId, List<String> userIds);
}
