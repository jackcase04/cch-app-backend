package com.cch.cch_app.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String fullname;
    private String username;
    private String token;
    private long expiresIn;

    public AuthResponse(String fullname, String username, String token, long expiresIn) {
        this.fullname = fullname;
        this.username = username;
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
