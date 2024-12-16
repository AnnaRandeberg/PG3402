package org.quizapp.flashcardservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlashcardDTO {
    private String questionText;
    private String answer;
}