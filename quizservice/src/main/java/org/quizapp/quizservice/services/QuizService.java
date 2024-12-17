package org.quizapp.quizservice.services;

import org.quizapp.quizservice.model.Quiz;

import java.util.List;

public interface QuizService {

    Quiz retrieveQuiz(int quizId);

    Quiz addQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Quiz getQuizById(int quizId);

    void deleteQuiz(int quizId);


    void saveQuiz(Quiz quiz);


    void trackQuizStart(int quizId, String userId);

    boolean validateAnswer(int quizId, int questionId, String userAnswer);

}
