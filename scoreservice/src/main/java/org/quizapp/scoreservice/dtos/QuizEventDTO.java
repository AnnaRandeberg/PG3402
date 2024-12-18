package org.quizapp.scoreservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizEventDTO {
    private Long userId;
    private Long quizId;
    private int points;
    private String title;
    private String subject;
    private String role;
    private String email;
}

