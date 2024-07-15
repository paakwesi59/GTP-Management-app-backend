package Repo;

import Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    User findByUsername(String username);

    User findByResetToken(String token);

    boolean existsByEmail(String email);
}
