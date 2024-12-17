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

    private final Set<String> registeredEmails = ConcurrentHashMap.newKeySet();

    @RabbitListener(queues = "${amqp.queue.user.name}")
    public void handleUserCreatedEvent(Map<String, Object> event) {
        log.info("Received event: {}", event);
        String email = (String) event.get("email");
        if (email != null && !email.isBlank()){
            registeredEmails.add(email);
            log.info("Added email to registered list: {}", email);
        } else {
            log.warn("Received event without email or invalid email: {}", event);
        }
    }


    public boolean isEmailNotRegistered(String email) {
        boolean notRegistered = !registeredEmails.contains(email);
        log.info("Checking if email is NOT registered: {} -> {}", email, notRegistered);
        return notRegistered;
    }

}

