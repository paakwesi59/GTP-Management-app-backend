//package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;
//
//import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
//import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
//import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo.UserRepository;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImplementation implements UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final EmailService emailService;
//    private final JavaMailSender mailSender;
//
//    @Override
//    public User inviteUser(String email, Role role) throws MessagingException {
//        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8); // Shorter temporary password
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(temporaryPassword));
//        user.setRole(role);
//        user.setTemporaryPassword(true);
//        userRepository.save(user);
//        emailService.sendInvitationEmail(email, temporaryPassword);
//        return user;
//    }
//
//    @Override
//    public Optional<User> findUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public boolean changePassword(String email, String oldPassword, String newPassword) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isEmpty() || !passwordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
//            return false;
//        }
//        User user = optionalUser.get();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setTemporaryPassword(false);
//        userRepository.save(user);
//        return true;
//    }
//
//    @Override
//    public void sendResetToken(String email) throws MessagingException {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            String token = UUID.randomUUID().toString();
//            user.setResetToken(token);
//            user.setTokenExpiryDate(Instant.now().plus(Duration.ofHours(1))); // Token valid for 1 hour
//            userRepository.save(user);
//            emailService.sendResetTokenEmail(email, token);
//        }
//    }
//
//    @Override
//    public boolean resetPassword(String token, String newPassword) {
//        Optional<User> userOptional = Optional.ofNullable(userRepository.findByResetToken(token));
//        if (userOptional.isEmpty() || userOptional.get().isResetTokenExpired()) {
//            return false; // Invalid or expired token
//        }
//        User user = userOptional.get();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setResetToken(null); // Clear the reset token
//        user.setTokenExpiryDate(null); // Clear the token expiry date
//        userRepository.save(user);
//        return true;
//    }
//
//    @Override
//    public boolean login(String email, String password) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            return false; // User not found
//        }
//        User user = userOptional.get();
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            return false; // Password does not match
//        }
//
//        // Check if it's a first-time login with temporary password
//        if (user.isTemporaryPassword()) {
//            // Set flag that password change is required
//            user.setTemporaryPassword(false); // This will trigger password change prompt
//            userRepository.save(user);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean isFirstTimeLogin(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            return false; // User not found
//        }
//        User user = userOptional.get();
//        return user.isTemporaryPassword();
//    }
//
//}

package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JavaMailSender mailSender;

    @Override
    public User inviteUser(String email, Role role) throws MessagingException {
        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8); // Shorter temporary password
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(temporaryPassword));
        user.setRole(role);
        user.setTemporaryPassword(true);
        userRepository.save(user);
        emailService.sendInvitationEmail(email, temporaryPassword);
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty() || !passwordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
            return false;
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTemporaryPassword(false);
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
            user.setTokenExpiryDate(Instant.now().plus(Duration.ofHours(1))); // Token valid for 1 hour
            userRepository.save(user);
            emailService.sendResetTokenEmail(email, token);
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByResetToken(token));
        if (userOptional.isEmpty() || userOptional.get().isResetTokenExpired()) {
            return false; // Invalid or expired token
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear the reset token
        user.setTokenExpiryDate(null); // Clear the token expiry date
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false; // User not found
        }
        User user = userOptional.get();
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean isFirstTimeLogin(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return false; // User not found
        }
        User user = userOptional.get();
        return user.isTemporaryPassword();
    }
}
