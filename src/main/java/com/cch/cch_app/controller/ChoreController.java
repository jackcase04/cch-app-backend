package com.cch.cch_app.controller;

import com.cch.cch_app.exception.NoChoreException;
import com.cch.cch_app.responses.ErrorResponse;
import com.cch.cch_app.service.ChoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/chores")
public class ChoreController {
    private final ChoreService choreService;

    @Autowired
    public ChoreController(ChoreService choreService) {
        this.choreService = choreService;
    }

    @GetMapping
    public ResponseEntity<?> getChores(

        @RequestParam(required = false) String name,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "M/d/yyyy") LocalDate date){

        try {
            if (name != null && date != null) {
                return ResponseEntity
                        .ok(choreService.getChoresByNameAndDate(name, date));
            } else if (name != null) {
                return ResponseEntity
                        .ok(choreService.getChoresFromName(name));
            } else {
                return ResponseEntity
                        .ok(choreService.getChores());
            }
        } catch (NoChoreException e) {
            return ResponseEntity
                    .ok(new ErrorResponse(e.getMessage()));
        }
    }
}
