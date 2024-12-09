package org.quizapp.quizservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.eventdriven.QuizEventPublisher;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.model.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizEventPublisher quizEventPublisher;

    @Override
    public Quiz retrieveQuiz(Long quizId) {
        return quizRepository.findQuizByQuizId(quizId);
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        Quiz savedQuiz = quizRepository.save(quiz);
        quizEventPublisher.publishQuizEvent(savedQuiz, "created");
        return savedQuiz;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findQuizByQuizId(quizId);
    }

    @Override
    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
        quizEventPublisher.publishQuizEvent(new Quiz(), "deleted");
    }

}
