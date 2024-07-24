package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    boolean existsByEmail(String email);
    boolean validatePassword(User user, String password);
    boolean processForgotPassword(String email);
    boolean resetPassword(String token, String newPassword);

    static boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }

    boolean checkPassword(Optional<User> user, String password);

    <T> Optional<Optional<User>> findUserByEmail(String email);
    User inviteUser(String email, Role role) throws MessagingException;
}