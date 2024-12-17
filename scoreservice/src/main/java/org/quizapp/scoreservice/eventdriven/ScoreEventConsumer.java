package org.quizapp.scoreservice.eventdriven;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.scoreservice.model.Score;
import org.quizapp.scoreservice.repository.ScoreRepository;
import org.quizapp.scoreservice.services.ScoreService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreEventConsumer {

    private final ScoreRepository scoreRepository;

    @RabbitListener(queues = "${amqp.queue.complete.name}")
    public void handleQuizCompleteEvent(Map<String, Object> event) {
        log.info("Received QuizCompleteEvent: {}", event);

        Long userId = Long.parseLong(event.get("userId").toString());
        Long quizId = Long.parseLong(event.get("quizId").toString());
        int points = Integer.parseInt(event.get("points").toString());
        String title = (String) event.get("title");
        String subject = (String) event.get("subject");
        String role = (String) event.get("role");

        Score score = new Score();
        score.setUserId(userId);
        score.setQuizId(quizId);
        score.setPoints(points);
        score.setQuizTitle(title);
        score.setSubject(subject);
        score.setRole(role);

        scoreRepository.save(score);

        log.info("Score saved for user {} on quiz {} with points {}", userId, quizId, points);
    }




}


