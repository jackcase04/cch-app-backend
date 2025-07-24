package com.cch.cch_app.job;

import com.cch.cch_app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationsJob {
    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000)
    public void checkForChores() {
        System.out.println("Job running at: " + new Date());

        notificationService.sendNotification("Test", "Hello from backend!", "");

    }
}
