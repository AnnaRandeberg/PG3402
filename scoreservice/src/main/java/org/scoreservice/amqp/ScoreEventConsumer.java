package org.scoreservice.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoreservice.model.Score;
import org.scoreservice.repository.ScoreRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreEventConsumer {

    private final ScoreRepository scoreRepository;

    @RabbitListener(queues = "quiz.queue")
    public void handleQuizCompletedEvent(Score score) {
        log.info("Received event: {}", score);
        scoreRepository.save(score);
    }
}
