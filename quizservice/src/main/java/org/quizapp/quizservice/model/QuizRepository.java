package org.quizapp.quizservice.model;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface QuizRepository extends CrudRepository<Quiz, Long> {

    Optional<Quiz> findById(Long id);
}
