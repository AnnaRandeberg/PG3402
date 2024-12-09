package org.quizapp.quizservice.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizRepository extends CrudRepository<Quiz, Long> {


    Quiz findQuizByQuizId(Long quizId);


    List<Quiz> findAll();
}
