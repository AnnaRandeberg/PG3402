package org.quizapp.quizservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizStartDTO {
    private String quizTitle; // Tittel på quizen
    private List<QuestionDTO> questions; // Liste over spørsmål

    @Data
    @AllArgsConstructor
    public static class QuestionDTO {
        private Long questionId;       // ID for spørsmålet
        private String questionText;   // Selve spørsmålet
    }
}
