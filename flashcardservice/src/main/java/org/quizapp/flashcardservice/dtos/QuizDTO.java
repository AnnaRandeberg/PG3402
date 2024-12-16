package org.quizapp.flashcardservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class QuizDTO {
    private Long quizId;
    private String title;
    private String chapter;
    private String subject;
    private List<QuestionDTO> questions;
}