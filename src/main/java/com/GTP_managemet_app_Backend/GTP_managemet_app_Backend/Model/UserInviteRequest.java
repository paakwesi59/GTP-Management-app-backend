package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;

public class UserInviteRequest {
    private String email;
    private Role role;
    private String name;

    public UserInviteRequest(String email, Role role, String name) {
        this.email = email;
        this.role = role;
        this.name = name;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
