package org.quizapp.quizservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.QuizEventDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
@RequiredArgsConstructor
@Slf4j
@Service

public class QuizEventPublisher {


    private final AmqpTemplate amqpTemplate;

    @Value("${amqp.exchange.name}")
    private String exchange;

    public void publishQuizEvent(int userId, String email, int quizId, int points, String title, String subject, String role) {
        Map<String, Object> event = Map.of(
                "userId", userId,
                "email", email,
                "quizId", quizId,
                "points", points,
                "title", title,
                "subject", subject,
                "role", role
        );

        log.info("Publishing QuizCompleteEvent: {}", event);
        amqpTemplate.convertAndSend("learningApp.exchange", "quiz.complete", event);
    }



}


