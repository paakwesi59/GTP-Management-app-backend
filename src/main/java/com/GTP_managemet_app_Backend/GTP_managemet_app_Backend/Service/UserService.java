package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.UserInviteRequest;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User inviteUser(String name, String email, Role role) throws MessagingException;
    List<User> inviteUsers(List<UserInviteRequest> userInviteRequests) throws MessagingException;
    Optional<User> findUserByEmail(String email);
    boolean changePassword(String email, String oldPassword, String newPassword, String confirmPassword);
    void sendResetToken(String email) throws MessagingException;
    boolean resetPassword(String token, String newPassword, String confirmPassword);
    boolean login(String email, String password);
    boolean isFirstTimeLogin(String email);
    void confirmAccount(String email) throws MessagingException;
}