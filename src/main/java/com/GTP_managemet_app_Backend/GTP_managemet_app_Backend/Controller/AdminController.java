package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Specialization;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.UserInviteRequest;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PostMapping("/invite")
    public ResponseEntity<?> inviteUser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("role") Role role, @RequestParam("specialization") Specialization specialization) {
        try {
            User invitedUser = userService.inviteUser(name, email, role, specialization);
            return new ResponseEntity<>(invitedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send invitation email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulk-invite")
    public ResponseEntity<?> inviteUsers(@RequestBody List<UserInviteRequest> userInviteRequests) {
        try {
            List<User> invitedUsers = userService.inviteUsers(userInviteRequests);
            return new ResponseEntity<>(invitedUsers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Failed to send one or more invitation emails.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
