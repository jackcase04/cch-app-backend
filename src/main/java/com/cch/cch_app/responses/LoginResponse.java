package com.cch.cch_app.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String fullname;
    private String token;
    private long expiresIn;

    public LoginResponse(String fullname, String token, long expiresIn) {
        this.fullname = fullname;
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
