package com.cch.cch_app.controller;

import com.cch.cch_app.exception.NameAlreadyRegisteredException;
import com.cch.cch_app.exception.NameNotAllowedException;
import com.cch.cch_app.exception.InvalidLoginException;
import com.cch.cch_app.responses.LoginResponse;
import com.cch.cch_app.responses.ErrorResponse;
import com.cch.cch_app.service.AuthenticationService;
import com.cch.cch_app.service.JwtService;
import com.cch.cch_app.model.User;
import com.cch.cch_app.dto.LoginUserDto;
import com.cch.cch_app.dto.RegisterUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private  final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto dto) {
        try {
            User user = authenticationService.signup(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user);
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
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse(authenticatedUser.getFullname(), jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (InvalidLoginException e) {
            return ResponseEntity.
                    status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        }

    }
}
