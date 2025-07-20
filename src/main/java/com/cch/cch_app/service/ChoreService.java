package com.cch.cch_app.service;

import com.cch.cch_app.exception.NoChoreException;
import com.cch.cch_app.repository.ChoreRepository;
import com.cch.cch_app.model.Chore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChoreService {
    private final ChoreRepository choreRepository;

    @Autowired
    public ChoreService(ChoreRepository choreRepository) {
        this.choreRepository = choreRepository;
    }

    public List<Chore> getChores() {
        List<Chore> chores = choreRepository.findAll();

        if (chores.isEmpty()) {
            throw new NoChoreException("No chores returned");
        }

        return chores;
    }

    public List<Chore> getChoresFromName(String name) {
        List<Chore> chores = choreRepository.findByName(name);

        if (chores.isEmpty()) {
            throw new NoChoreException("No chores for name provided");
        }

        return chores;
    }

    public List<Chore> getChoresByNameAndDate(String name, String date) {
        List<Chore> chores = choreRepository.findByNameAndDate(name, date);

        if (chores.isEmpty()) {
            throw new NoChoreException("No chores for that name today");
        }

        return chores;
    }
}
