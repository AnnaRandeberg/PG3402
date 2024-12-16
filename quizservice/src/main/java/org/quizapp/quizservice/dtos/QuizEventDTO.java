package org.quizapp.quizservice.dtos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuizEventDTO {
    String userId;
    Long quizId;
    int points;


}
