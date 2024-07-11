package Service;

import Model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean existsByEmail(String email);
    User findByUsername(String username);
    boolean validatePassword(User user, String password);
    boolean processForgotPassword(String email);
    boolean resetPassword(String token, String newPassword);
    boolean changePassword(String username, String oldPassword, String newPassword);

}
