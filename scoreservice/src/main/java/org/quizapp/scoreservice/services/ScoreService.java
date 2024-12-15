package org.quizapp.scoreservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.scoreservice.dtos.QuizEventDTO;
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

    public Score addScore(Score score) {
        return scoreRepository.save(score);
    }

    public void saveScore(QuizEventDTO event) {
        Score score = new Score();
        score.setUserId(event.getUserId());
        score.setQuizId(event.getQuizId());
        score.setPoints(event.getPoints());
        scoreRepository.save(score);
    }
}
