package Service;

import Model.User;
import Repo.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    @Autowired
    private JavaMailSender mailSender;


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

    public void sendResetToken(String email) throws MessagingException {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            userRepository.save(user);
            sendEmail(user.getEmail(), token);
        }
    }

    private void sendEmail(String to, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Password Reset Request");
        String content = "<p>Click the link below to reset your password:</p>"
                + "<p><a href=\"http://localhost:8080/reset-password?token=" + token + "\">Reset Password</a></p>";
        helper.setText(content, true);

        mailSender.send(message);
    }
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
