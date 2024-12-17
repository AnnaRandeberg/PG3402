package org.quizapp.scoreservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.scoreservice.model.Score;
import org.quizapp.scoreservice.repository.ScoreRepository;
import org.quizapp.scoreservice.services.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreRepository scoreRepository;
    private final ScoreService scoreService;

    @GetMapping
    public ResponseEntity<List<Score>> getScores() {
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok(scores);

    }
}
