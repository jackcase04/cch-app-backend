package com.cch.cch_app.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutResponse {
    private String message;

    public LogoutResponse(String message) {
        this.message = message;
    }
}
