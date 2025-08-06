package com.cch.cch_app.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String fullname;
    private String username;

    public AuthResponse(String fullname, String username) {
        this.fullname = fullname;
        this.username = username;
    }
}
