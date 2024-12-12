package org.quizapp.quizservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quiz {

    @Id
    private Long quizId;
    private String title;
    private String subject;
    private String chapter;
}
