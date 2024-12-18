package org.quizapp.quizservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.QuizCompleteDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
@RequiredArgsConstructor
@Slf4j
@Service

public class QuizEventPublisher {


    private final AmqpTemplate amqpTemplate;

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    public void publishQuizEvent(QuizCompleteDTO quizComplete) {
        Map<String, Object> event = new HashMap<>();
        event.put("userId", quizComplete.getUserId());
        event.put("email", quizComplete.getEmail());
        event.put("quizId", quizComplete.getQuizId());
        event.put("points", quizComplete.getCorrectAnswers());
        event.put("title", quizComplete.getTitle());
        event.put("subject", quizComplete.getSubject());
        event.put("role", quizComplete.getRole());

        log.info("Publishing QuizEvent: {}", event);
        amqpTemplate.convertAndSend(exchangeName, "quiz.complete", event);
    }

}


