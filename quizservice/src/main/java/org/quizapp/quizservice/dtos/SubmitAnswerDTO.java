package org.quizapp.quizservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitAnswerDTO {
    private String email;
    private Long questionId;
    private String answer;

}
