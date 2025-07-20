package com.cch.cch_app.service;

import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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
        LocalTime test = LocalTime.now();
        System.out.println("Current local time: " + test);
        System.out.println("Reminder time is provided in format: " + time);

        user.setReminderTime(test);
        userRepository.save(user);
    }
}
