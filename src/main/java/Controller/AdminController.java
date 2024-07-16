package Controller;

import Model.Role;
import Model.User;
import Service.UserService;
import Service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @PostMapping("/invite")
    public User inviteUser(@RequestParam String email, @RequestParam Role role) {
        return userServiceImplementation.inviteUser(email, role);
    }

    @PostMapping("/bulk-invite")
    public List<User> inviteUsers(@RequestBody List<String> emails, @RequestParam Role role) {
        List<User> users = new ArrayList<>();
        for (String email : emails) {
            users.add(userServiceImplementation.inviteUser(email, role));
        }
        return users;
    }
}

