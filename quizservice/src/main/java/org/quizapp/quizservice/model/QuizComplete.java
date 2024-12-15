package org.quizapp.quizservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizComplete {
    private String userId;
    private Long quizId;
    private int correctAnswers;
    private int totalQuestions;
}