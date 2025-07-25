package com.cch.cch_app.repository;

import com.cch.cch_app.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
    Name findByName(String name);
    List<Name> findByAccountassociatedFalse();
}

