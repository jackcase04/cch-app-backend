package com.cch.cch_app.service;

import com.cch.cch_app.exception.NoChoreException;
import com.cch.cch_app.repository.ChoreRepository;
import com.cch.cch_app.model.Chore;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ChoreService {
    private final ChoreRepository choreRepository;

    public ChoreService(ChoreRepository choreRepository) {
        this.choreRepository = choreRepository;
    }

    public Chore getChores(String name) {
        Chore chore = choreRepository.findByNameAndDate(name, LocalDate.now());

        if (chore == null) {
            throw new NoChoreException("No chores returned");
        }

        return chore;
    }
}
