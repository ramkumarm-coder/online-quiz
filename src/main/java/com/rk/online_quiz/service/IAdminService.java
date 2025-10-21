package com.rk.online_quiz.service;

import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.dto.admin.request.QuizQuestionCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;

import java.util.List;

public interface IAdminService {

    List<QuizCreation> findAllQuizzes();

    void createNewQuiz(QuizCreationRequest request);

    void updateQuiz(long id, String title, String description,int duration);

    boolean isQuizTitleAlreadyPresent(String quizTitle);

    void deleteQuiz(long quizId);

    QuizCreation findQuizById(Long id);
    QuizQuestionCreation findQuizQuestionById(Long id);

    List<QuizQuestionCreation> findAllQuizQuestions();

    void updateOrInsertQuizQuestion(Long quizId, QuizQuestionCreationRequest request);

    void deleteQuizQuestion(Long questionId);
}
