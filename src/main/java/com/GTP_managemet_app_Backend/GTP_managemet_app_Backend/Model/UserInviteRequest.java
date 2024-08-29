package com.GTP_managemet_app_Backend.GTP_managemet_app_Backend.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInviteRequest {
    // Getters and setters
    private String email;
    private Role role;
    private String name;
    private Specialization specialization;

    public UserInviteRequest(String email, Role role, String name, Specialization specialization) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.specialization = specialization;
    }

}
