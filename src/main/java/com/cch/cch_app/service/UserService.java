package com.cch.cch_app.service;

import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
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
}
