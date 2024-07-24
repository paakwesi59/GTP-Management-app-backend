package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserServiceImplementation;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    private final UserServiceImplementation userServiceImplementation;


    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        try {
            Optional<User> user = userService.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (userService.checkPassword(user, password)) {
                if (user.get().getTemporaryPassword()) {
                    // Redirect to change password page if it's a temporary password
                    model.addAttribute("email", email);
                    return "redirect:/change-password";
                }
                model.addAttribute("message", "Login successful.");
                return "home"; // Redirect to a home page or dashboard
            } else {
                model.addAttribute("message", "Invalid email or password.");
                return "login";
            }
        } catch (UsernameNotFoundException e) {
            model.addAttribute("message", "User not found.");
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while logging in.");
        }
        return "login";
    }

    // Password reset beginning
    // Method to handle password reset
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userServiceImplementation.sendResetToken(email);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send email");
        }
        return ResponseEntity.ok("Password reset link has been sent");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestBody ResetPasswordRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ResetPasswordRequest.MessageResponse("Passwords do not match."));

        }

        boolean result = userService.resetPassword(request.getToken(), request.getPassword());



        if (result) {
            return ResponseEntity.ok(new ResetPasswordRequest.MessageResponse("Password has been reset successfully."));
        } else {
            return ResponseEntity.badRequest().body(new ResetPasswordRequest.MessageResponse("Invalid or expired token."));
        }
    }
    //Dto classes
    class ResetPasswordRequest {
        private String token;
        private String password;
        private String confirmPassword;
        // getters and setters

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }

        static class MessageResponse {
            private String message;

            public MessageResponse(String message) {
                this.message = message;
            }

            // getter and setter

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }


            @PostMapping("/change-password")
            public String processChangePassword(@RequestParam("oldPassword") String oldPassword,
                                                @RequestParam("newPassword") String newPassword,
                                                @RequestParam("confirmNewPassword") String confirmNewPassword,
                                                RedirectAttributes redirectAttributes) {
                if (!newPassword.equals(confirmNewPassword)) {
                    redirectAttributes.addFlashAttribute("errorMessage", "New passwords do not match.");
                    return "redirect:/change-password";
                }
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String email = auth.getName();

                boolean result = UserService.changePassword(email, oldPassword, newPassword);

                if (result) {
                    redirectAttributes.addFlashAttribute("successMessage", "Password has been changed successfully.");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Old password is incorrect.");
                    return "redirect:/change-password";
                }

                return "redirect:/loginpage";
            }
        }

    }
}