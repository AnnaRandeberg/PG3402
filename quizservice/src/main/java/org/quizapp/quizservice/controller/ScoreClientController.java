package org.quizapp.quizservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/quizapi/scores")
public class ScoreClientController {

    private final RestTemplate restTemplate;

    public ScoreClientController() {
        this.restTemplate = new RestTemplate();
    }

    @GetMapping("/{userId}")
    public List<?> getUserScores(@PathVariable String userId) {
        String scoreServiceUrl = "http://scoreservice:8082/scores";
        List<?> scores = restTemplate.getForObject(scoreServiceUrl, List.class);
        return scores.stream()
                .filter(score -> ((String) ((Map) score).get("userId")).equals(userId))
                .toList();
    }
}
