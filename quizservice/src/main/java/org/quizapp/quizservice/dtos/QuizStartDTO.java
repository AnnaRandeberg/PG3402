package org.quizapp.quizservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizStartDTO {
    private String quizTitle;
    private List<QuestionDTO> questions;

    @Data
    @AllArgsConstructor
    public static class QuestionDTO {
        private Long questionId;
        private String questionText;
    }
}
