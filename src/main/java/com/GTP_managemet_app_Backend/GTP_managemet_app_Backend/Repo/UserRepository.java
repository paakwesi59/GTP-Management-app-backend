package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User findByResetToken(String token);
    long countByRole(Role role);
    List<User> findAllBySpecialization(String specialization);
}
