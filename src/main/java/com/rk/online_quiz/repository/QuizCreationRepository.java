package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.QuizCreation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizCreationRepository  extends JpaRepository<QuizCreation, Long> {
}
