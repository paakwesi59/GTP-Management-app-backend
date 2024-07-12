package Controller;

import Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {


    private UserService userService;

    // Password reset beginning
    // Method to handle password reset
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

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "change-password";
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
        String username = auth.getName();

        boolean result = UserService.changePassword(username, oldPassword, newPassword);

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