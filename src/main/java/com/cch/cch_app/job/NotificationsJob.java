package com.cch.cch_app.job;

import com.cch.cch_app.model.Chore;
import com.cch.cch_app.model.User;
import com.cch.cch_app.repository.ChoreRepository;
import com.cch.cch_app.repository.UserRepository;
import com.cch.cch_app.service.NotificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class NotificationsJob {
    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final ChoreRepository choreRepository;

    public NotificationsJob(NotificationService notificationService, UserRepository userRepository, ChoreRepository choreRepository) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.choreRepository = choreRepository;
    }

    @Scheduled(cron = "1 * * * * *")
    public void checkForChores() {
        LocalTime time = LocalTime.now().withSecond(0).withNano(0);

        List<User> users = userRepository.findByReminderTimeAndExpopushtokenIsNotNull(time);

        System.out.println("Found " + users.size() + " users with reminder time " + time);

        for (User user : users) {
            Chore chore = choreRepository.findByNameAndDate(user.getFullname(), LocalDate.now());
            if (chore != null) {
                System.out.println("Sending notification to " + user.getFullname());
                notificationService.sendNotification("You have 1 chore today", chore.getDescription(), user.getExpopushtoken());
            } else {
                System.out.println("User " + user.getFullname() + " had reminder now but didn't have a chore");
            }
        }
    }
}
