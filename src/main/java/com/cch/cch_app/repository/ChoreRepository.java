package com.cch.cch_app.repository;

import com.cch.cch_app.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChoreRepository extends JpaRepository<Chore, Long> {
    Chore findByNameAndDate(String name, LocalDate date);
    List<Chore> findByName(String name);

    List<Chore> findByDate(LocalDate date);
}

