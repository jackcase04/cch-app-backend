package com.cch.cch_app.repository;

import com.cch.cch_app.model.Chore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChoreRepository extends JpaRepository<Chore, Long> {
    List<Chore> findByNameAndDate(String name, String date);
    List<Chore> findByName(String name);
}

