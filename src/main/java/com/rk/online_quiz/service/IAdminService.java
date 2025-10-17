package com.rk.online_quiz.service;

import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;

import java.util.List;

public interface IAdminService {

    List<QuizCreation> findAllQuizzes();

    void createNewQuiz(QuizCreationRequest request);

    boolean isQuizTitleAlreadyPresent(String quizTitle);

    void deleteQuiz(long quizId);
}
