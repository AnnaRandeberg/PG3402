package org.quizapp.flashcardservice.repository;


import org.quizapp.flashcardservice.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface FlashcardsRepository extends JpaRepository<Flashcard, Long> {
}
