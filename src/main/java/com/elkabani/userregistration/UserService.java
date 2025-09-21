package com.elkabani.userregistration;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    // Run #1 (default): EmailNotificationService will be injected because it's @Primary
    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // If you want to force SMS for Run #2, replace the constructor above with this:
    /*
    import org.springframework.beans.factory.annotation.Qualifier;
    public UserService(UserRepository userRepository, @Qualifier("sms") NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }
    */

    public boolean registerUser(User user) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        // Duplicate check via repository
        if (userRepository.findByEmail(user.getEmail()) != null) {
            System.out.println("Duplicate: user with email " + user.getEmail() + " already registered.");
            return false;
        }

        userRepository.save(user);

        // Send confirmation
        String msg = "Welcome " + user.getName() + "! Your account is registered.";
        notificationService.send(msg, user.getEmail());

        return true;
    }
}
