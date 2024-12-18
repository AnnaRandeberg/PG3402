package org.quizapp.scoreservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.quizapp.scoreservice.dtos.QuizEventDTO;
import org.quizapp.scoreservice.model.Score;
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

        try {
            Score score = new Score();
            score.setUserId(event.getUserId());
            score.setQuizId(event.getQuizId());
            score.setPoints(event.getPoints());
            score.setQuizTitle(event.getTitle());
            score.setSubject(event.getSubject());
            score.setRole(event.getRole());
            score.setEmail(event.getEmail());

            scoreService.saveScore(score);
            log.info("Score saved successfully: {}", score);
        } catch (Exception e) {
            log.error("Failed to process QuizEvent: {}", e.getMessage());
        }
    }

}