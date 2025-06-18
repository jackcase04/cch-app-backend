package com.cch.cch_app.repository;

import com.cch.cch_app.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoreRepository extends JpaRepository<Chore, Long> {
    Chore findByNameAndDate(String name, String date);
}

