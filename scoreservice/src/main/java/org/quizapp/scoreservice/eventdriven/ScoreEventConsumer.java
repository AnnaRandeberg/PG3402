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
        log.info("Processing QuizCompleteEvent: {}", event);

        int userId = (int) event.get("userId");
        int quizId = (int) event.get("quizId");
        int points = (int) event.get("score"); // poengsummen kommer fra "score"

        String quizTitle = (String) event.get("title");
        String subject = (String) event.get("subject");
        String role = (String) event.get("role");

        log.info("User {} completed quiz {} with score {}", userId, quizId, points);

        // Oppretter en ny Score-instans og lagrer den
        Score scoreEntity = new Score();
        scoreEntity.setUserId(userId);
        scoreEntity.setQuizId(quizId);
        scoreEntity.setPoints(points); // Setter poengsummen til "points"
        scoreEntity.setQuizTitle(quizTitle);
        scoreEntity.setSubject(subject);
        scoreEntity.setRole(role);

        scoreRepository.save(scoreEntity);
        log.info("Score saved successfully for user {}", userId);
    }



}


