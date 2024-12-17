package org.quizapp.scoreservice.repository;

import org.quizapp.scoreservice.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> { }

