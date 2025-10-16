package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.QuizQuestionCreation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizQuestionCreationRepository  extends JpaRepository<QuizQuestionCreation,Long> {
}
