package org.quizapp.quizservice.dtos;

import lombok.Value;

@Value
public class QuizCompleteDTO {
    String userId;
    Long quizId;
    int correctAnswers;
    int totalQuestions;
}