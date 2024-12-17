package org.quizapp.quizservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizComplete {
    private int userId;
    private int quizId;
    private int correctAnswers;
    private int totalQuestions;
}
