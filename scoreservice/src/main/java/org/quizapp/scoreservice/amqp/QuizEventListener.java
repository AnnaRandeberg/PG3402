package org.quizapp.scoreservice.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.quizapp.scoreservice.dtos.QuizEventDTO;
import org.quizapp.scoreservice.services.ScoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizEventListener {

    private final ScoreService scoreService;

    @RabbitListener(queues = "${amqp.queue.complete.name}")
    public void handleQuizEvent(QuizEventDTO event) {
        log.info("Received QuizEvent: {}", event);
        scoreService.saveScore(event);
    }
}
