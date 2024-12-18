package org.quizapp.quizservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizDTO {
    private Long quizId;
    private String title;
    private String chapter;
    private String subject;
}
