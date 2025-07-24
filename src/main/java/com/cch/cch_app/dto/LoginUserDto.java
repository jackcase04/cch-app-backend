package com.cch.cch_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    private String username;
    private String password;
    private String expopushtoken;

    public LoginUserDto(String username, String password, String expopushtoken) {
        this.username = username;
        this.password = password;
        this.expopushtoken = expopushtoken;
    }
}
