package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;

import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,20}$",
            message = "Password must be between 8 and 20 characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;
    private boolean temporaryPassword;
    private Role role;
    private String resetToken;
    private Instant tokenExpiryDate;
    private String name;
    private boolean confirmed;
    private String accessToken;
    private String refreshToken;
    private String passwordChangeLink;
    private Specialization specialization;

    public User(String id, String email, String password, boolean temporaryPassword, Role role, String resetToken, Instant tokenExpiryDate, String name, boolean confirmed, String accessToken, String refreshToken, String passwordChangeLink, Specialization specialization) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.temporaryPassword = temporaryPassword;
        this.role = role;
        this.resetToken = resetToken;
        this.tokenExpiryDate = tokenExpiryDate;
        this.name = name;
        this.confirmed = confirmed;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.passwordChangeLink = passwordChangeLink;
        this.specialization = specialization;
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
        return confirmed;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPasswordChangeLink() {
        return passwordChangeLink;
    }

    public void setPasswordChangeLink(String passwordChangeLink) {
        this.passwordChangeLink = passwordChangeLink;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public boolean isResetTokenExpired() {
        if (tokenExpiryDate == null) {
            return true;
        }
        return Instant.now().isAfter(tokenExpiryDate);
    }
}
