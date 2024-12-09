package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.services.QuizService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/quizapi")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("/quizzes")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    @GetMapping("/quizzes/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @DeleteMapping("/quizzes/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }

}
