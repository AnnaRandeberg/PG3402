package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizapi")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    // Henter alle tilgjengelige quizzer
    @GetMapping("/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    // Oppretter en ny quiz
    @PostMapping("/quizzes")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    // Henter en spesifikk quiz basert på ID
    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    // Sletter en quiz basert på ID
    @DeleteMapping("/quizzes/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
}
