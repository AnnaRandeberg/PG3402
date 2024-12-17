package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.*;
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
            @RequestBody SubmitAnswerDTO submitAnswer) {

        if (userEventConsumer.isEmailNotRegistered(submitAnswer.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User not registered.");
        }

        boolean isCorrect = quizService.validateAnswer(
                quizId, submitAnswer.getQuestionId(), submitAnswer.getAnswer()
        );

        if (isCorrect) {
            Quiz quiz = quizService.getQuizById(quizId);
            String role;
            try {
                role = userEventConsumer.getRoleByEmail(submitAnswer.getEmail());
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Role not found for the given user.");
            }

            int correctAnswers = isCorrect ? 1 : 0;
            int totalQuestions = 1;

            Long userId = userEventConsumer.getUserIdByEmail(submitAnswer.getEmail());

            QuizCompleteDTO quizComplete = new QuizCompleteDTO(
                    userId,
                    submitAnswer.getEmail(),
                    quizId,
                    correctAnswers,
                    totalQuestions,
                    quiz.getTitle(),
                    quiz.getSubject(),
                    role,
                    submitAnswer.getQuestionId(),
                    submitAnswer.getAnswer()
            );
            quizComplete.setUserId(userEventConsumer.getUserIdByEmail(submitAnswer.getEmail()));
            quizEventPublisher.publishQuizEvent(quizComplete);
        }

        return ResponseEntity.ok(Map.of(
                "questionId", submitAnswer.getQuestionId(),
                "isCorrect", isCorrect
        ));
    }





    @PostMapping("/quizzes/{quizId}/start")
    public ResponseEntity<?> startQuiz(
            @PathVariable Long quizId,
            @RequestBody Map<String, String> requestBody) {

        String email = requestBody.get("email");
        if (userEventConsumer.isEmailNotRegistered(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("User not registered.");
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

   /* //denne funker ikke
    // Opprett quiz kun hvis bruker er ADMIN
    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody QuizRequestDTO quizRequest) {
        try {

            if (!"admin@gmail.com".equalsIgnoreCase(quizRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create quizzes.");
            }

            quizService.saveQuiz(quiz);
            return ResponseEntity.ok("Quiz created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
*/
    //denne funker
    @PostMapping("/complete")
    public ResponseEntity<String> completeQuiz(@RequestBody QuizCompleteDTO quizComplete) {
        Quiz quiz = quizService.getQuizById(quizComplete.getQuizId());
        if (quiz == null) {
            return ResponseEntity.badRequest().body("Quiz not found");
        }

        int points = (quizComplete.getCorrectAnswers() * 100) / quizComplete.getTotalQuestions();
        String role;
        try {
            role = userEventConsumer.getRoleByEmail(quizComplete.getEmail());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Role not found for the given user.");
        }

        quizEventPublisher.publishQuizEvent(quizComplete);

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
