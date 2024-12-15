package org.quizapp.scoreservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizEventDTO {
    String userId;
    Long quizId;
    int points;

}

