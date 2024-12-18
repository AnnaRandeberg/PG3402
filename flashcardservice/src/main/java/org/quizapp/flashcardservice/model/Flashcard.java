package org.quizapp.flashcardservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "flashcards")
@Getter
@Setter
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("flashcardId")
    private Long flashcardId;

    @Column(nullable = false)
    @JsonProperty("questionText")
    private String questionText;

    @Column(nullable = false)
    @JsonProperty("answer")
    private String answer;
}
