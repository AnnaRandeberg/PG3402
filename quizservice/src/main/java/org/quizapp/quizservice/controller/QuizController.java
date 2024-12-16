package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.QuizCompleteDTO;
import org.quizapp.quizservice.dtos.UserDTO;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.quizapp.quizservice.eventdriven.QuizEventPublisher;
import org.quizapp.quizservice.model.QuizComplete;


import java.util.List;
@Slf4j
@RestController
@RequestMapping("/quizapi")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizEventPublisher quizEventPublisher;

    @GetMapping("/quiz")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }


   /* @PostMapping("/quiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz savedQuiz = quizService.addQuiz(quiz);
        return ResponseEntity.ok(savedQuiz);
    }*/

    // Opprett quiz kun hvis bruker er ADMIN
    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody Quiz quiz, @RequestParam String email) {
        try {
            UserDTO userDTO = getUserDTO(email);

            if (!"ADMIN".equals(userDTO.getRole())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create quizzes.");
            }

            quizService.saveQuiz(quiz);
            return ResponseEntity.ok("Quiz created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/complete")
    public ResponseEntity<String> completeQuiz(@RequestBody QuizCompleteDTO quizComplete) {
        Quiz quiz = quizService.getQuizById(quizComplete.getQuizId());
        if (quiz == null) {
            return ResponseEntity.badRequest().body("Quiz not found");
        }

        int points = (quizComplete.getCorrectAnswers() * 100) / quizComplete.getTotalQuestions();
        quizEventPublisher.publishQuizEvent(quizComplete.getUserId(), quizComplete.getQuizId(), points);

        return ResponseEntity.ok("Quiz completed and points published!");
    }




    @GetMapping("/quiz/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }

    @DeleteMapping("/quiz/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }
    private UserDTO getUserDTO(String email) {
        UserDTO userDTO = new UserDTO(email, "ADMIN");
        return userDTO;
    }

}
