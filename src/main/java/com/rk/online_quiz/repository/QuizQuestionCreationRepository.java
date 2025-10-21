package com.rk.online_quiz.repository;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionCreationRepository  extends JpaRepository<QuizQuestionCreation,Long> {
    List<QuizQuestionCreation> findByQuizCreation(QuizCreation quizCreation);
}
