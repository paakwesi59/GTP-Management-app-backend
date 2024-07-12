package Service;

import Model.Role;
import Model.User;
import Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public User inviteUser(String email, Role role) {
        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8); // Shorter temporary password

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(temporaryPassword));
        user.setRole(role);
        user.setTemporaryPassword(true);

        userRepository.save(user);

        emailService.sendInvitationEmail(email, temporaryPassword);

        return user;
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    // Other methods like updatePassword, authenticateUser, etc.
}
