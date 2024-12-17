package org.quizapp.quizservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class UserEventConsumer {

    private final Set<String> registeredEmails = new HashSet<>();

    @RabbitListener(queues = "${amqp.queue.user.name}")
    public void handleUserCreatedEvent(Map<String, Object> event) {
        log.info("Received event: {}", event);
        String email = (String) event.get("email");
        if (email != null) {
            registeredEmails.add(email);
            log.info("Added email to registered list: {}", email);
        } else {
            log.warn("Received event without email: {}", event);
        }
    }



    public boolean isEmailRegistered(String email) {
        return registeredEmails.contains(email);
    }
}

