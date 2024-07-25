package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;//package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User inviteUser(String email, Role role) throws MessagingException;
    Optional<User> findUserByEmail(String email);
    boolean changePassword(String email, String oldPassword, String newPassword);
    void sendResetToken(String email) throws MessagingException;
    boolean resetPassword(String token, String newPassword);
    boolean login(String email, String password);
    boolean isFirstTimeLogin(String email);
}

