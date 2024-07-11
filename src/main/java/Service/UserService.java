//package Service;
//
//import Model.User;
//import org.springframework.stereotype.Service;
//
//@Service
//public interface UserService {
//    boolean existsByEmail(String email);
//    User findByUsername(String username);
//    boolean validatePassword(User user, String password);
//    boolean processForgotPassword(String email);
//    boolean resetPassword(String token, String newPassword);
//    boolean changePassword(String username, String oldPassword, String newPassword);
//
//}

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

@Service
public interface UserService {
    boolean existsByEmail(String email);

    User findByUsername(String username);

    boolean validatePassword(User user, String password);

    boolean processForgotPassword(String email);

    boolean resetPassword(String token, String newPassword);

    static boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }

    boolean checkPassword(Optional<User> user, String password);

    <T> Optional<Optional<User>> findUserByEmail(String email);

    @Service
    public class UserServices implements UserDetailsService {

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

        public void updatePassword(User user, String newPassword) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setTemporaryPassword(false);
            userRepository.save(user);
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

        public boolean checkPassword(User user, String password) {
            return passwordEncoder.matches(password, user.getPassword());
        }
    }
}
