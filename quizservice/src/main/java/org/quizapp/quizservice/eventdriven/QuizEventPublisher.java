package org.quizapp.quizservice.eventdriven;

import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.model.Quiz;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public void publishQuizEvent(Quiz quiz, String eventType) {
        String routingKey = "quiz." + eventType.toLowerCase();
        log.info("Publishing event to RabbitMQ: {} with routing key: {}", quiz, routingKey);
        amqpTemplate.convertAndSend(exchangeName, routingKey, quiz);
    }
}
