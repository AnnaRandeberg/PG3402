package org.quizapp.flashcardservice.services;
import org.quizapp.flashcardservice.dtos.FlashcardDTO;
import org.quizapp.flashcardservice.dtos.QuizDTO;
import org.quizapp.flashcardservice.model.Flashcard;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.quizapp.flashcardservice.repository.FlashcardsRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class FlashcardService {

    private final RestTemplate restTemplate;
    private final String quizServiceUrl = "http://quizservice:8081/quizapi/quiz";
    private final FlashcardsRepository flashcardsRepository;

    public List<Flashcard> getAllFlashcards() {
        return flashcardsRepository.findAll();
    }
    public FlashcardService(RestTemplate restTemplate, FlashcardsRepository flashcardsRepository) {
        this.restTemplate = restTemplate;
        this.flashcardsRepository = flashcardsRepository;
    }

    public Flashcard saveFlashcard(Flashcard flashcard) {
        return flashcardsRepository.save(flashcard);
    }

    public List<FlashcardDTO> getFlashcardsFromQuiz(Long quizId) {
        QuizDTO quiz = restTemplate.getForObject(quizServiceUrl + "/" + quizId, QuizDTO.class);

        if (quiz == null || quiz.getQuestions() == null) {
            return new ArrayList<>(); // Returner tom liste ved feil
        }

        List<FlashcardDTO> flashcards = new ArrayList<>();
        quiz.getQuestions().forEach(q ->
                flashcards.add(new FlashcardDTO(q.getQuestionText(), q.getAnswer()))
        );

        return flashcards;
    }
    public QuizDTO getQuizById(Long quizId) {
        return restTemplate.getForObject(quizServiceUrl + "/" + quizId, QuizDTO.class);
    }
}

