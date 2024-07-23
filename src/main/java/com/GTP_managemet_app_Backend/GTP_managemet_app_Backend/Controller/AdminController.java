package Controller;

import Model.Role;
import Model.User;
import Service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {


    private final UserService userService;

    @PostMapping("/invite")
    public ResponseEntity<User> inviteUser(@RequestParam("email") String email, @RequestParam("role") Role role) throws MessagingException {
        return new ResponseEntity<>(userService.inviteUser(email, role), HttpStatus.OK);
    }

    @PostMapping("/bulk-invite")
    public List<User> inviteUsers(@RequestBody List<String> emails, @RequestParam Role role) throws MessagingException {
        List<User> users = new ArrayList<>();
        for (String email : emails) {
            users.add(userService.inviteUser(email, role));
        }
        return users;
    }
}

