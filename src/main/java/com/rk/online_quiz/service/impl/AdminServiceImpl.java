package com.rk.online_quiz.service.impl;

import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.repository.QuizCreationRepository;
import com.rk.online_quiz.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final QuizCreationRepository quizCreationRepository;

    @Override
    public List<QuizCreation> findAllQuizzes() {
        return quizCreationRepository.findAll();
    }

    @Override
    public void createNewQuiz(QuizCreationRequest request) {
        QuizCreation quizCreation = QuizCreation.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .timeLimitMinutes(request.getTimeLimitMinutes())
                .build();
        quizCreationRepository.save(quizCreation);
    }

    @Override
    public boolean isQuizTitleAlreadyPresent(String quizTitle) {
        return quizCreationRepository.findByTitle(quizTitle).isPresent();
    }

    @Override
    public void deleteQuiz(long quizId) {
        quizCreationRepository.findById(quizId).orElseThrow(
                () -> new ResolutionException("Quiz not found for this id: " + quizId)
        );

        quizCreationRepository.deleteById(quizId);
    }
}
