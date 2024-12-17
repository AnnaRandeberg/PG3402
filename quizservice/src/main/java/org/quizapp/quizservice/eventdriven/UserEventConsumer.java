package org.quizapp.quizservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UserEventConsumer {

    private final Map<String, String> userRoles = new ConcurrentHashMap<>();
    private final Map<String, Long> userIds = new ConcurrentHashMap<>();

    @RabbitListener(queues = "${amqp.queue.user.name}")
    public void handleUserCreatedEvent(Map<String, Object> event) {
        log.info("Received event: {}", event);
        String email = (String) event.get("email");
        String role = (String) event.get("role");
        Long userId = Long.parseLong(event.get("userId").toString());
        if (email != null && role != null && userId != null) {
            userIds.put(email, userId);
            userRoles.put(email, role);
            log.info("Stored userId: {} and role: {} for email: {}", userId, role, email);
        } else {
            log.warn("Invalid UserCreatedEvent: {}", event);
        }
    }
    public boolean isEmailNotRegistered(String email) {
        return !userRoles.containsKey(email);
    }
    public String getRoleByEmail(String email) {
        String role = userRoles.get(email);
        if (role == null) {
            throw new IllegalStateException("Role not found for email: " + email);
        }
        return role;
    }

    public Long getUserIdByEmail(String email) {
        return userIds.getOrDefault(email, null);
    }


}

