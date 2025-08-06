package com.cch.cch_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyValidator {

    @Value("${app.api.key}")
    private String validApiKey;

    public boolean isValidApiKey(String apiKey) {
        return validApiKey != null && validApiKey.equals(apiKey);
    }
}
