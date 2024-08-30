package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "projects")
public class Project {

    @Id
    private String id;
    private String name;
    private String filePath; // Path to the uploaded file
    private String createdBy;
    private List<String> assignedUsers;
    private Instant createdDate;
    private Instant modifiedDate;

    public Project() {
    }

    public Project(String id, String name, String filePath, String createdBy, List<String> assignedUsers, Instant createdDate, Instant modifiedDate) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.createdBy = createdBy;
        this.assignedUsers = assignedUsers;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<String> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<String> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
