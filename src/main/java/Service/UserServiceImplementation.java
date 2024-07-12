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
public class UserServiceImplementation implements UserService {
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

    public Optional<Optional<User>> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public boolean validatePassword(User user, String password) {
        return false;
    }

    @Override
    public boolean processForgotPassword(String email) {
        return false;
    }



    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean checkPassword(Optional<User> user, String password) {
        return false;
    }
    // Other methods like updatePassword, authenticateUser, etc.



        // method for change password
    public boolean changePassword1(String username, String oldPassword, String newPassword ) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }


//reset password method
    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<Model.User> userOptional = Optional.ofNullable(userRepository.findByResetToken(token));
        if (!userOptional.isPresent() || userOptional.get().isResetTokenExpired()) {
            return false; // Invalid or expired token
        }
        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Clear the reset token
        user.setResetTokenExpiryDate(null); // Clear the token expiry date

        userRepository.save(user);
        return true;
    }
}
