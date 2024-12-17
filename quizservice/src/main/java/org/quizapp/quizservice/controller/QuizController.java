package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.QuizCompleteDTO;
import org.quizapp.quizservice.dtos.QuizDTO;
import org.quizapp.quizservice.dtos.QuizStartDTO;
import org.quizapp.quizservice.dtos.UserDTO;
import org.quizapp.quizservice.eventdriven.UserEventConsumer;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.quizapp.quizservice.eventdriven.QuizEventPublisher;
import org.quizapp.quizservice.model.QuizComplete;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/quizapi")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizEventPublisher quizEventPublisher;
    private final UserEventConsumer userEventConsumer;

  /*  //denne funker
    @GetMapping("/quiz")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuizzes();
    }*/

    @GetMapping
    public List<QuizDTO> getQuizzes() {
        return quizService.getAllQuizzes().stream()
                .map(quiz -> new QuizDTO(
                        quiz.getQuizId(),
                        quiz.getTitle(),
                        quiz.getChapter(),
                        quiz.getSubject()))
                .toList();
    }



    @PostMapping("/quizzes/{quizId}/answer")
    public ResponseEntity<?> submitAnswer(
            @PathVariable Long quizId,
            @RequestParam String email, // Endret til email
            @RequestBody Map<String, String> answerPayload) {

        if (!userEventConsumer.isEmailRegistered(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User not registered. Please register to submit an answer.");
        }

        String questionId = answerPayload.get("questionId");
        String answer = answerPayload.get("answer");

        boolean isCorrect = quizService.validateAnswer(quizId, Long.parseLong(questionId), answer);

        if (isCorrect) {
            // Publiser en hendelse til RabbitMQ
            quizEventPublisher.publishQuizEvent(email, quizId, 1);
        }
        return ResponseEntity.ok(Map.of(
                "questionId", questionId,
                "isCorrect", isCorrect
        ));
    }


    @PostMapping("/quizzes/{quizId}/start")
    public ResponseEntity<?> startQuiz(@PathVariable Long quizId, @RequestParam String email) {
        // Sjekk om brukeren er registrert (RabbitMQ data)
        if (!userEventConsumer.isEmailRegistered(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User not registered. Please register to start a quiz.");
        }

        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("quizId", quiz.getQuizId());
        response.put("title", quiz.getTitle());
        response.put("chapter", quiz.getChapter());
        response.put("subject", quiz.getSubject());
        response.put("questions", quiz.getQuestions().stream()
                .map(question -> Map.of(
                        "questionId", question.getId(),
                        "questionText", question.getQuestionText()
                ))
                .toList());

        return ResponseEntity.ok(response);
    }


   /* @PostMapping("/quiz")
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz savedQuiz = quizService.addQuiz(quiz);
        return ResponseEntity.ok(savedQuiz);
    }*/

    //denne funker ikke
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

    //denne funker
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



    //denne funker
    @GetMapping("/quiz/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }


    // ikke testet, men denne må fikses på for kun admin kan slette quizzer
    @DeleteMapping("/quiz/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
    }

    private UserDTO getUserDTO(String email) {
        UserDTO userDTO = new UserDTO(email, "ADMIN");
        return userDTO;
    }



}
