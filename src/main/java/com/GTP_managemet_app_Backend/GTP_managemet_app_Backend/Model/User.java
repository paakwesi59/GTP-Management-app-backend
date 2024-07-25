//package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.Instant;
//import java.util.Collection;
//import java.util.List;
//
//@Document(collection = "users")
//public class User implements UserDetails {
//
//    @Id
//    private String id;
//    private String email;
//    private String password;
//    private boolean temporaryPassword;
//    private Role role;
//    private String resetToken;
//    private Instant tokenExpiryDate;
//
//    public User(String id, String email, String password, boolean temporaryPassword, Role role, String resetToken, Instant tokenExpiryDate) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.temporaryPassword = temporaryPassword;
//        this.role = role;
//        this.resetToken = resetToken;
//        this.tokenExpiryDate = tokenExpiryDate;
//    }
//
//    public User() {
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isTemporaryPassword() {
//        return temporaryPassword;
//    }
//
//    public void setTemporaryPassword(boolean temporaryPassword) {
//        this.temporaryPassword = temporaryPassword;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getResetToken() {
//        return resetToken;
//    }
//
//    public void setResetToken(String resetToken) {
//        this.resetToken = resetToken;
//    }
//
//    public Instant getTokenExpiryDate() {
//        return tokenExpiryDate;
//    }
//
//    public void setTokenExpiryDate(Instant tokenExpiryDate) {
//        this.tokenExpiryDate = tokenExpiryDate;
//    }
//
//    public boolean isResetTokenExpired() {
//        if (tokenExpiryDate == null) {
//            return true; // Assuming null means expired or invalid
//        }
//        return Instant.now().isAfter(tokenExpiryDate);
//    }
//}

package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;
    private String email;
    private String password;
    private boolean temporaryPassword;
    private Role role;
    private String resetToken;
    private Instant tokenExpiryDate;

    public User(String id, String email, String password, boolean temporaryPassword, Role role, String resetToken, Instant tokenExpiryDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.temporaryPassword = temporaryPassword;
        this.role = role;
        this.resetToken = resetToken;
        this.tokenExpiryDate = tokenExpiryDate;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTemporaryPassword() {
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

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Instant getTokenExpiryDate() {
        return tokenExpiryDate;
    }

    public void setTokenExpiryDate(Instant tokenExpiryDate) {
        this.tokenExpiryDate = tokenExpiryDate;
    }

    public boolean isResetTokenExpired() {
        if (tokenExpiryDate == null) {
            return true; // Assuming null means expired or invalid
        }
        return Instant.now().isAfter(tokenExpiryDate);
    }
}
