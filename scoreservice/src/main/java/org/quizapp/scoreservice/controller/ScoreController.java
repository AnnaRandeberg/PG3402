package org.quizapp.scoreservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.scoreservice.model.Score;
import org.quizapp.scoreservice.repository.ScoreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreRepository scoreRepository;

    //denne funker
    @GetMapping
    public List<Score> getAllScores() {
        return scoreRepository.findAll();
    }


    //denne funker
    @PostMapping
    public Score addScore(@RequestBody Score score) {
        return scoreRepository.save(score);
    }
}
