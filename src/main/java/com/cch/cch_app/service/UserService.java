package com.cch.cch_app.service;

import com.cch.cch_app.exception.NoUserException;
import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByName(String name) {
        User user = userRepository.findByFullname(name);

        if (user == null) {
            throw new NoUserException("User does not exist");
        }

        return user;
    }

    public void setUserReminder(User user, String time) {
        if (!time.equals("reset")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            LocalTime realtime = LocalTime.parse(time, formatter);

            System.out.println("Parsed reminder time: " + realtime);

            user.setReminderTime(realtime);
            userRepository.save(user);
        } else {
            user.setReminderTime(null);
            userRepository.save(user);
        }
    }

    public void logoutUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        user.orElseThrow().setReminderTime(null);
        user.orElseThrow().setExpopushtoken(null);

        userRepository.save(user.orElseThrow());
    }
}
