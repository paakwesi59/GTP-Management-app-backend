package Controller;

import Model.Role;
import Model.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private UserService userService;

    @PostMapping("/invite")
    public User inviteStudent(@RequestParam String email) {
        return userService.inviteUser(email, Role.STUDENT);
    }
}
