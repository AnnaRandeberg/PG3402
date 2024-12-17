package org.quizapp.quizservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
public class QuizCompleteDTO {
    private Long userId;
    private String email;
    private Long quizId;
    private int correctAnswers;
    private int totalQuestions;
    private String title;
    private String subject;
    private String role;
    private Long questionId;
    private String answer;
}
