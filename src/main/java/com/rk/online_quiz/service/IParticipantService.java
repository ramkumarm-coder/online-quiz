package com.rk.online_quiz.service;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;

import java.util.List;

public interface IParticipantService {

    List<QuizCreation> findAllAvailableQuizzes();

    List<QuizQuestionCreation> findAllQuestionsByQuizId(long quizId);
    QuizCreation findQuizById(long quizId);
}
