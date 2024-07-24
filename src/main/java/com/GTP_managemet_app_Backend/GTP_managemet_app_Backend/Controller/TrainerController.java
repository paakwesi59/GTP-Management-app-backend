package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Controller;

import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.Role;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model.User;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserService;
import com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Service.UserServiceImplementation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TrainerController {

    private final UserService userService;

    private final UserServiceImplementation userServiceImplementation;

    @PostMapping("/invite")
    public User inviteStudent(@RequestParam String email) throws MessagingException {
        return userServiceImplementation .inviteUser(email, Role.TRAINEE);
    }
}
