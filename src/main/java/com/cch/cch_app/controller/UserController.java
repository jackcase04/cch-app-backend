package com.cch.cch_app.controller;

import com.cch.cch_app.model.User;
import com.cch.cch_app.responses.LogoutResponse;
import com.cch.cch_app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/reminder")
    public ResponseEntity<User> authenticatedUser(
            @RequestParam(required = true) String time,
            @RequestParam(required = true) String username
    ) {

        User currentUser = userService.findUserByName(username);

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
