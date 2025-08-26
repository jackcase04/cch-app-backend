package com.cch.cch_app.controller;

import com.cch.cch_app.exception.NoUserException;
import com.cch.cch_app.model.User;
import com.cch.cch_app.responses.LogoutResponse;
import com.cch.cch_app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getUser(
            @RequestParam(required = true) String fullname
    ) {
        try {
            User user = userService.findUserByName(fullname);

            return ResponseEntity.ok(user);
        } catch (NoUserException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/reminder")
    public ResponseEntity<User> authenticatedUser(
            @RequestParam(required = true) String time,
            @RequestParam(required = true) String fullname
    ) {

        User currentUser = userService.findUserByName(fullname);

        userService.setUserReminder(currentUser, time);
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logoutUser(
            @RequestParam(required = true) String username
    ) {
        userService.logoutUser(username);

        return ResponseEntity.ok(new LogoutResponse("logout successfull"));
    }
}
