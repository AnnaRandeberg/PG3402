package org.scoreservice.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.scoreservice.client.QuizServiceClient;
import org.scoreservice.model.Score;
import org.scoreservice.repository.ScoreRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreListener {

    private final ScoreRepository scoreRepository;
    private final QuizServiceClient quizServiceClient;

    @RabbitListener(queues = "quiz.queue")

   /* public void handleQuizEvent(Score score) {
        try {
            log.info("Received quiz event: {}", score);
            scoreRepository.save(score);
        } catch (Exception e) {
            log.error("Failed to process quiz event: {}", score, e);
        }*/

    @RabbitListener(queues = "quiz.queue")
    public void handleQuizCompletedEvent(Map<String, Object> message) {
        log.info("Received quiz completion event: {}", message);

        try {
            String userId = (String) message.get("userId");
            Long quizId = Long.valueOf(message.get("quizId").toString());
            int points = Integer.parseInt(message.get("points").toString());

            Score score = new Score();
            score.setUserId(userId);
            score.setQuizId(quizId);
            score.setPoints(points);

            scoreRepository.save(score);
            log.info("Score saved successfully: {}", score);
        } catch (Exception e) {
            log.error("Error processing quiz completion event: {}", message, e);
        }
    }


}
