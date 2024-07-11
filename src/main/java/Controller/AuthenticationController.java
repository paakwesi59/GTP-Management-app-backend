package Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    //Password reset beginning
    //Method to handle password reset
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
}

class ResetPasswordRequest{
    private String token;
    private String password;
    private String confirmPassword;
    //getters and setters

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

        //getter and setter

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

