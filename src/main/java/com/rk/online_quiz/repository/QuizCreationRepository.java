package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.QuizCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuizCreationRepository  extends JpaRepository<QuizCreation, Long> {

    Optional<QuizCreation> findByTitle(String title);
}
