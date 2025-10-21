package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.QuizQuestOptionCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizQuestOptionCreationRepository  extends JpaRepository<QuizQuestOptionCreation, Long> {
}
