package org.quizapp.scoreservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.scoreservice.model.Score;
import org.quizapp.scoreservice.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    public void saveScore(Score score) {
        scoreRepository.save(score);
        log.info("Score: {}", score);
    }
}
