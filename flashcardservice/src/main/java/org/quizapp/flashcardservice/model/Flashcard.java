package org.quizapp.flashcardservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flashcards")
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flashcardId;

    private String questionText;
    private String answer;
}
