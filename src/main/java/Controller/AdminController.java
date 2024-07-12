package Controller;

import Model.Role;
import Model.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/invite")
    public User inviteUser(@RequestParam String email, @RequestParam Role role) {
        return userService.inviteUser(email, role);
    }

    @PostMapping("/bulk-invite")
    public List<User> inviteUsers(@RequestBody List<String> emails, @RequestParam Role role) {
        List<User> users = new ArrayList<>();
        for (String email : emails) {
            users.add(userService.inviteUser(email, role));
        }
        return users;

    }
}

