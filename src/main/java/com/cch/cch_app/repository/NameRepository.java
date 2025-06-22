package com.cch.cch_app.repository;

import com.cch.cch_app.model.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NameRepository extends JpaRepository<Name, Long> {
    Name findByName(String name);
}

