package org.quizapp.quizservice.services;

import org.quizapp.quizservice.model.Quiz;

import java.util.List;

public interface QuizService {

    Quiz retrieveQuiz(Long quizId);

    Quiz addQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Quiz getQuizById(Long quizId);

    void deleteQuiz(Long quizId);


    void saveQuiz(Quiz quiz);


    void trackQuizStart(Long quizId, String userId);

    boolean validateAnswer(Long quizId, Long questionId, String userAnswer);

}
