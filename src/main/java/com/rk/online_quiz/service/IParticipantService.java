package com.rk.online_quiz.service;

import com.rk.online_quiz.entity.QuizCreation;

import java.util.List;

public interface IParticipantService {

    List<QuizCreation> findAllAvailableQuizzes();
}
