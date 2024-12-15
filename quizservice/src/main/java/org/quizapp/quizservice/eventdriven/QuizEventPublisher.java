package org.quizapp.quizservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@Slf4j
@Service

public class QuizEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public QuizEventPublisher(
            AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") String exchangeName) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    public void publishQuizEvent(String userId, Long quizId, int points) {
        Map<String, Object> event = Map.of(
                "userId", userId,
                "quizId", quizId,
                "points", points
        );
        String routingKey = "quiz.complete";
        log.info("Publishing QuizEvent: {}", event);
        amqpTemplate.convertAndSend(exchangeName, routingKey, event);
    }
}


