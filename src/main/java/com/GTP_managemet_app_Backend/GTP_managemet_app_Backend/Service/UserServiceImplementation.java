package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Configure.JwtTokenUtil;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Specialization;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.UserInviteRequest;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;

    // Define the password pattern
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");

    // Inviting user to the platform
    @Override
    public User inviteUser(String name, String email, Role role, Specialization specialization) throws MessagingException {
        // Check if email already exists
        Optional<User> existingUser = findUserByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        // Generate a temporary password for user
        String temporaryPassword = generateTemporaryPassword();
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(temporaryPassword));
        user.setRole(role);
        user.setSpecialization(specialization);
        user.setTemporaryPassword(true);
        user.setConfirmed(false);
        String passwordChangeLink = "http://localhost:8080/api/user/change-password?token=" + UUID.randomUUID().toString();
        user.setPasswordChangeLink(passwordChangeLink);
        userRepository.save(user);
        emailService.sendInvitationEmail(name, email, temporaryPassword, passwordChangeLink);
        return user;
    }

    // Inviting bilk users to the platform
    @Override
    public List<User> inviteUsers(List<UserInviteRequest> userInviteRequests) throws MessagingException {
        List<User> invitedUsers = new ArrayList<>();
        List<String> duplicateEmails = new ArrayList<>();

        for (UserInviteRequest request : userInviteRequests) {
            Optional<User> existingUser = findUserByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                duplicateEmails.add(request.getEmail());
                continue;  // Skip this user if already exists
            }

            // Generate a temporary password for user
            String temporaryPassword = generateTemporaryPassword();
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(temporaryPassword));
            user.setRole(request.getRole());
            user.setSpecialization(request.getSpecialization());
            user.setTemporaryPassword(true);
            user.setConfirmed(false);
            String passwordChangeLink = "http://localhost:8080/api/user/change-password?token=" + UUID.randomUUID().toString();
            user.setPasswordChangeLink(passwordChangeLink);

            userRepository.save(user);
            emailService.sendInvitationEmail(request.getName(), request.getEmail(), temporaryPassword, passwordChangeLink);

            invitedUsers.add(user);
        }

        // Check if email already exists
        if (!duplicateEmails.isEmpty()) {
            throw new IllegalArgumentException("Duplicate emails found: " + String.join(", ", duplicateEmails));
        }

        return invitedUsers;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Change password method
    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword, String confirmPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !passwordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new IllegalArgumentException("New password does not meet the required criteria.");
        }

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTemporaryPassword(false);
        user.setConfirmed(true);
        userRepository.save(user);
        return true;
    }

    @Override
    public void sendResetToken(String email) throws MessagingException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiryDate(Instant.now().plus(Duration.ofHours(1)));
            userRepository.save(user);
            emailService.sendResetTokenEmail(user.getName(), email, token);
        }
    }


    // Reset Password method
    @Override
    public boolean resetPassword(String token, String newPassword, String confirmPassword) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByResetToken(token));
        if (userOptional.isEmpty() || userOptional.get().isResetTokenExpired()) {
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
            throw new ConstraintViolationException("New password does not meet the required criteria", null);
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setTokenExpiryDate(null);
        userRepository.save(user);
        return true;
    }

    // Login method
    @Override
    public boolean login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            String accessToken = jwtTokenUtil.generateToken(user);
            String refreshToken = jwtTokenUtil.generateRefreshToken(user);

            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
        return matches;
    }

    // Check if it's a first login
    @Override
    public boolean isFirstTimeLogin(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false;
        }
        User user = userOptional.get();
        return user.isTemporaryPassword();
    }

    @Override
    public void confirmAccount(String email) throws MessagingException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setConfirmed(true);
            userRepository.save(user);
            emailService.sendAccountConfirmationEmail(user.getName(), email);
        }
    }

    private String generateTemporaryPassword() {
        return RandomStringUtils.random(8, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%^&+=!");
    }
}
