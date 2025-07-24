package com.cch.cch_app.repository;

import com.cch.cch_app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByFullname(String fullname);
    List<User> findByReminderTimeAndExpopushtokenIsNotNull(LocalTime reminderTime);
}


