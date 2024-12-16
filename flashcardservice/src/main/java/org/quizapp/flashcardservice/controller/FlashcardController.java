package org.quizapp.flashcardservice.controller;

import lombok.RequiredArgsConstructor;
import org.quizapp.flashcardservice.dtos.QuizDTO;
import org.quizapp.flashcardservice.model.Flashcard;
import org.quizapp.flashcardservice.services.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//server.port=8087
@RestController
@RequestMapping("/flashcards")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @GetMapping("/quiz/{id}")
    public ResponseEntity<QuizDTO> getFlashcardQuiz(@PathVariable Long id) {
        QuizDTO quiz = flashcardService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping
    public ResponseEntity<List<Flashcard>> getAllFlashcards() {
        return ResponseEntity.ok(flashcardService.getAllFlashcards());
    }

    @PostMapping
    public ResponseEntity<Flashcard> createFlashcard(@RequestBody Flashcard flashcard) {
        return ResponseEntity.ok(flashcardService.saveFlashcard(flashcard));
    }
}

