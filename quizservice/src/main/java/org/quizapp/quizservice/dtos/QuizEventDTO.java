package org.quizapp.quizservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuizEventDTO {
    private int userId;
    private int quizId;
    private int points;
    private String title;
    private String subject;
    private String role;

}
