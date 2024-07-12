package Service;

import Model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    boolean existsByEmail(String email);
    User findByUsername(String username);
    boolean validatePassword(User user, String password);
    boolean processForgotPassword(String email);
    boolean resetPassword(String token, String newPassword);

    static boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }

    boolean checkPassword(Optional<User> user, String password);

    <T> Optional<Optional<User>> findUserByEmail(String email);
}
