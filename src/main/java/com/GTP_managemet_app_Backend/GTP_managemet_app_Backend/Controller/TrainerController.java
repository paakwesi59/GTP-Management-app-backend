package Controller;

import Model.Role;
import Model.User;
import Service.UserService;
import Service.UserServiceImplementation;
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
        return userServiceImplementation .inviteUser(email, Role.STUDENT);
    }
}
