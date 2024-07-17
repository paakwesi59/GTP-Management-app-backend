package Service;

import Model.Role;
import Model.User;
import Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
public interface UserService {
    boolean existsByEmail(String email);


    User findByUsername(String username);

    boolean validatePassword(User user, String password);

    boolean processForgotPassword(String email);

    boolean resetPassword(String token, String newPassword);

    boolean changePassword(String username, String oldPassword, String newPassword);

    boolean checkPassword(Optional<User> user, String password);

    Optional<Optional<User>> findUserByEmail(String email);
}


@Service
class UserServices implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean processForgotPassword(String email) {
        // Implementation
        return false;
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        // Implementation
        return false;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        // Implementation
        return false;
    }

    @Override
    public boolean checkPassword(Optional<User> user, String password) {
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public Optional<Optional<User>> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        User user = optionalUser.get();
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .accountLocked(user.getTemporaryPassword())
                .build();
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setTemporaryPassword(false);
        userRepository.save(user);
    }

    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
