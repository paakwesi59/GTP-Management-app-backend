package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Configure.JwtTokenUtil;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Repo.UserRepository;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        boolean result = userService.login(email, password);
        if (result) {
            if (userService.isFirstTimeLogin(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("First time login, please change your password.");
            } else {
                User user = userService.findUserByEmail(email).orElseThrow(); // Assuming user exists
                String accessToken = jwtTokenUtil.generateToken(user);
                String refreshToken = jwtTokenUtil.generateRefreshToken(user);

                // Save tokens in the user object for tracking
                user.setAccessToken(accessToken);
                user.setRefreshToken(refreshToken);
                userRepository.save(user);

                return ResponseEntity.ok()
                        .header("Location", getRedirectURL(user.getRole()))
                        .body(Map.of("accessToken", accessToken, "refreshToken", refreshToken,"user", user.getRole()))    ;
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    private String getRedirectURL(Role role) {
        switch (role) {
            case ADMIN:
                return "/admin/dashboard";
            case TRAINER:
                return "/trainer/dashboard";
            case TRAINEE:
                return "/trainee/dashboard";
            default:
                return "/";
        }
    }

//    @PostMapping("/change-password")
//    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
//        try {
//            boolean result = userService.changePassword(email, oldPassword, newPassword);
//            if (result) {
//                return ResponseEntity.ok("Password changed successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password or user not found.");
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
@PostMapping("/change-password")
public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
    try {
        boolean result = userService.changePassword(email, oldPassword, newPassword);
        if (result) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password or user not found.");
        }
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws MessagingException {
        userService.sendResetToken(email);
        return ResponseEntity.ok("Password reset token sent to email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean result = userService.resetPassword(token, newPassword);
        if (result) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }

}
