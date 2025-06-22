package com.cch.cch_app.service;

import com.cch.cch_app.model.Name;
import com.cch.cch_app.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NameService {
    private final NameRepository nameRepository;

    @Autowired
    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public List<Name> getNames() {
        return nameRepository.findAll();
    }
}

