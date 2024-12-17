package org.quizapp.quizservice.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface QuizRepository extends CrudRepository<Quiz, Integer> {

    Optional<Quiz> findByQuizId(int quizId);

}
