package org.quizapp.quizservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.dtos.*;
import org.quizapp.quizservice.eventdriven.UserEventConsumer;
import org.quizapp.quizservice.model.Question;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.quizapp.quizservice.eventdriven.QuizEventPublisher;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/quizapi")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final QuizEventPublisher quizEventPublisher;
    private final UserEventConsumer userEventConsumer;


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


    @PostMapping("/{quizId}/answer")
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


    @PostMapping("/{quizId}/start")
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


   //denne funker
    // Oppretter en quiz kun hvis bruker har admin mailen
    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody CreateQuizDTO createQuizDTO) {
        try {

            if (!"admin@gmail.com".equalsIgnoreCase(createQuizDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admins can create quizzes.");
            }

            Quiz quiz = new Quiz();
            quiz.setTitle(createQuizDTO.getTitle());
            quiz.setChapter(createQuizDTO.getChapter());
            quiz.setSubject(createQuizDTO.getSubject());

            List<Question> questions = createQuizDTO.getQuestions().stream()
                    .map(dto -> {
                        Question question = new Question();
                        question.setQuestionText(dto.getQuestionText());
                        question.setCorrectAnswer(dto.getCorrectAnswer());
                        return question;
                    }).collect(Collectors.toList());

            quiz.setQuestions(questions);

            quizService.saveQuiz(quiz);
            return ResponseEntity.ok("Quiz created!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }


    //denne funker
    @GetMapping("/quiz/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return quizService.getQuizById(id);
    }


}
