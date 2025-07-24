package com.cch.cch_app.service;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {
    private final PushClient pushClient;

    public NotificationService() {
        try {
            this.pushClient = new PushClient();
        } catch (PushClientException e) {
            throw new RuntimeException("Failed to initialize PushClient", e);
        }
    }

    public void sendNotification(String title, String body, String pushToken) {
        try {
            if (PushClient.isExponentPushToken(pushToken)) {
                // Create message using constructor
                ExpoPushMessage expoPushMessage = new ExpoPushMessage();
                expoPushMessage.getTo().add(pushToken);
                expoPushMessage.setTitle(title);
                expoPushMessage.setBody(body);

                List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
                expoPushMessages.add(expoPushMessage);

                // Send notification
                CompletableFuture<List<ExpoPushTicket>> messageRepliesFuture =
                        pushClient.sendPushNotificationsAsync(expoPushMessages);

                List<ExpoPushTicket> tickets = messageRepliesFuture.get();

                for (ExpoPushTicket ticket : tickets) {
                    System.out.println("Ticket ID: " + ticket.getId());
                    System.out.println("Status: " + ticket.getStatus());
                    if (ticket.getDetails() != null && ticket.getDetails().getError() != null) {
                        System.err.println("Error: " + ticket.getDetails().getError());
                    }
                }
            } else {
                System.err.println("Invalid push token: " + pushToken);
            }
        } catch (Exception e) {
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }
}