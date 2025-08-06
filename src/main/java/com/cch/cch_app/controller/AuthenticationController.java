package com.cch.cch_app.controller;

import com.cch.cch_app.exception.NameAlreadyRegisteredException;
import com.cch.cch_app.exception.NameNotAllowedException;
import com.cch.cch_app.exception.InvalidLoginException;
import com.cch.cch_app.responses.AuthResponse;
import com.cch.cch_app.responses.ErrorResponse;
import com.cch.cch_app.service.AuthenticationService;
import com.cch.cch_app.model.User;
import com.cch.cch_app.dto.LoginUserDto;
import com.cch.cch_app.dto.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(
            @RequestBody RegisterUserDto dto
    ) {
        try {
            User user = authenticationService.signup(dto);
            AuthResponse authResponse = new AuthResponse(user.getFullname(), user.getUsername());

            return ResponseEntity.ok(authResponse);
        } catch (InvalidLoginException e) {
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (NameNotAllowedException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (NameAlreadyRegisteredException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(
            @RequestBody LoginUserDto dto
    ) {
        try {
            User authenticatedUser = authenticationService.authenticate(dto);
            AuthResponse authResponse = new AuthResponse(authenticatedUser.getFullname(), authenticatedUser.getUsername());

            return ResponseEntity.ok(authResponse);
        } catch (InvalidLoginException e) {
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }
}