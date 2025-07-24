package com.cch.cch_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String full_name;
    private String username;
    private String password;
    private String expopushtoken;
}
