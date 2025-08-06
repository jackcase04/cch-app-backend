package com.cch.cch_app.controller;

import com.cch.cch_app.exception.NoChoreException;
import com.cch.cch_app.responses.ErrorResponse;
import com.cch.cch_app.service.ChoreService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chores")
public class ChoreController {
    private final ChoreService choreService;

    public ChoreController(ChoreService choreService) {
        this.choreService = choreService;
    }

    @GetMapping
    public ResponseEntity<?> getChores(
            @RequestParam String name
    ) {
        try {
            return ResponseEntity
                    .ok(choreService.getChores(name));

        } catch (NoChoreException e) {
            return ResponseEntity
                    .ok(new ErrorResponse(e.getMessage()));
        }
    }
}
