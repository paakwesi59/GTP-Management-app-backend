package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Specialization;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserServiceImplementation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainer")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TrainerController {

    private final UserServiceImplementation userServiceImplementation;

    @PostMapping("/invite")
    public ResponseEntity<?> inviteStudent(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("specialization") Specialization specialization) {
        try {
            User invitedUser = userServiceImplementation.inviteUser(name, email, Role.TRAINEE, specialization);
            return ResponseEntity.ok(invitedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send invitation email.");
        }
    }
}