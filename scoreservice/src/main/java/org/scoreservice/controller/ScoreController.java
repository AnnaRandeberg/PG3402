package org.scoreservice.controller;

import lombok.RequiredArgsConstructor;
import org.scoreservice.model.Score;
import org.scoreservice.repository.ScoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreRepository scoreRepository;

    @GetMapping
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }

    @PostMapping
    public Score addScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }
}
