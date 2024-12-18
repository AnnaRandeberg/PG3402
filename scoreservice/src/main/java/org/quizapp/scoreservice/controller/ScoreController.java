package org.quizapp.scoreservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.scoreservice.model.Score;
import org.quizapp.scoreservice.services.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @GetMapping
    public ResponseEntity<List<Score>> getScores() {
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok(scores);

    }
    @PostMapping("/email")
    public ResponseEntity<List<Score>> getScoresByEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<Score> scores = scoreService.getScoresByEmail(email);
        if (scores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(scores);
    }

}
