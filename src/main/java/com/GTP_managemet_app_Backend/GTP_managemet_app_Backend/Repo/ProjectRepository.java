package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project,String> {
}
