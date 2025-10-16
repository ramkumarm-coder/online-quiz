package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.AttemptedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptedQuizRepository extends JpaRepository<AttemptedQuiz, Long> {
}
