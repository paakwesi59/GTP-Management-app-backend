//package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;
//
//import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
//import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
//import jakarta.mail.MessagingException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/user")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
//        boolean result = userService.login(email, password);
//        if (result) {
//            // Check if it's the user's first login and return appropriate response
//            if (userService.isFirstTimeLogin(email)) {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("First time login, please change your password.");
//            } else {
//                return ResponseEntity.ok("Login successful.");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
//        }
//    }
//
//    @PostMapping("/change-password")
//    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
//        boolean result = userService.changePassword(email, oldPassword, newPassword);
//        if (result) {
//            return ResponseEntity.ok("Password changed successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password or user not found.");
//        }
//    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws MessagingException {
//        userService.sendResetToken(email);
//        return ResponseEntity.ok("Password reset token sent to email.");
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
//        boolean result = userService.resetPassword(token, newPassword);
//        if (result) {
//            return ResponseEntity.ok("Password reset successfully.");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
//        }
//    }
//
//}

package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        boolean result = userService.login(email, password);
        if (result) {
            // Check if it's the user's first login and return appropriate response
            if (userService.isFirstTimeLogin(email)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("First time login, please change your password.");
            } else {
                return ResponseEntity.ok("Login successful.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
        boolean result = userService.changePassword(email, oldPassword, newPassword);
        if (result) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password or user not found.");
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
