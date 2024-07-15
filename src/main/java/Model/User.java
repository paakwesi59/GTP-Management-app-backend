package Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Document(collation = "users")
public class User {

    @Getter
    @Id
    private String id;
    @Getter
    private String email;
    @Getter
    private String password;
    private boolean temporaryPassword;
    @Getter
    private Role role;

    public User(String id, String email, String password, boolean temporaryPassword, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.temporaryPassword = temporaryPassword;
        this.role = role;
    }

    public User() {

    }

    public boolean getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(boolean temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isResetTokenExpired() {
        return false;
    }

    public void setResetToken(Object o) {
    }

    public void setResetTokenExpiryDate(Object o) {

    }

}
