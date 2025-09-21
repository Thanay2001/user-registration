package com.elkabani.userregistration;

import org.springframework.stereotype.Service;

@Service("sms")
public class SMSNotificationService implements NotificationService {
    @Override
    public void send(String message, String recipientEmail) {
        System.out.println("Recipient" + recipientEmail);
        System.out.println("SMS Notification: " + message);
    }
}
