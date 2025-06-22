package com.cch.cch_app.controller;

import com.cch.cch_app.model.Name;
import com.cch.cch_app.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/names")
public class NameController {
    private final NameService nameService;

    @Autowired
    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping
    public List<Name> getNames() {
        return nameService.getNames();
    }
}