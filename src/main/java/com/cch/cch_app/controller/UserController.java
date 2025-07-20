package com.cch.cch_app.controller;

import com.cch.cch_app.model.User;
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
            @RequestParam(required = true) String time) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        userService.setUserReminder(currentUser, time);
        return ResponseEntity.ok(currentUser);
    }
}
