package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.AttemptedQuizQuestion;
import com.rk.online_quiz.entity.AttemptedQuizQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptedQuizQuestionRepository extends JpaRepository<AttemptedQuizQuestion, AttemptedQuizQuestionId> {
}
