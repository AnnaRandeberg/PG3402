package org.quizapp.quizservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateQuizDTO {
    private String email;
    private String title;
    private String chapter;
    private String subject;
    /*private List<QuizStartDTO.QuestionDTO> questions;*/
    private List<CreateQuestionDTO> questions;


    @Getter
    @Setter
    public static class CreateQuestionDTO {
        private Long questionId;
        private String questionText;
        private String correctAnswer;
    }
}
