package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.AttemptedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptedQuizRepository extends JpaRepository<AttemptedQuiz, Long> {
}
