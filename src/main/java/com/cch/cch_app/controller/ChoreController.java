package com.cch.cch_app.controller;

import com.cch.cch_app.service.ChoreService;
import com.cch.cch_app.model.Chore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chores")
public class ChoreController {
    private final ChoreService choreService;

    @Autowired
    public ChoreController(ChoreService choreService) {
        this.choreService = choreService;
    }

    @GetMapping
    public List<Chore> getChores(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String date){

        if (name != null && date != null) {
            return choreService.getChoresByNameAndDate(name, date);
        } else if (name != null) {
            return choreService.getChoresFromName(name);
        } else {
            return choreService.getChores();
        }
    }
}
