package org.quizapp.quizservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizCompleteDTO {
    private int userId;
    private String email;
    private int quizId;
    private int correctAnswers;
    private int totalQuestions;
}
