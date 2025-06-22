package com.cch.cch_app.controller;

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
        // we need to make sure name exists
        User user = authenticationService.signup(dto);
        if (user.getFull_name() != null) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(user);
        } else {
            ErrorResponse err = new ErrorResponse("Name not found in valid names");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(err);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
