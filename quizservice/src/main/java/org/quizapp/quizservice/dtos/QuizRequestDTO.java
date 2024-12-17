package org.quizapp.quizservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRequestDTO {
    private String email; // Admin sin e-post
    private String title;
    private String chapter;
    private String subject;
}
