package org.quizapp.quizservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class QuizEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public QuizEventPublisher(
            final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    public void publishQuizEvent(String userId, Long quizId, int points) {
        Map<String, Object> message = new HashMap<>();
        message.put("userId", userId);
        message.put("quizId", quizId);
        message.put("points", points);

        String routingKey = "quiz.completed";
        amqpTemplate.convertAndSend(exchangeName, routingKey, message);
        log.info("Published quiz event to RabbitMQ: {}", message);
    }

}

