package org.quizapp.loginservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.loginservice.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventPublisher {

    private final AmqpTemplate amqpTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    public void publishUserCreatedEvent(User user) {
        Map<String, Object> event = Map.of(
                "userId", user.getUserId(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
        String routingKey = "user.created";
        log.info("Publishing UserCreatedEvent: {}", event);
        amqpTemplate.convertAndSend(exchangeName, routingKey, event);
    }
}
