package com.rk.online_quiz.service.impl;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;
import com.rk.online_quiz.exception.ResourceNotFoundException;
import com.rk.online_quiz.repository.QuizCreationRepository;
import com.rk.online_quiz.repository.QuizQuestionCreationRepository;
import com.rk.online_quiz.service.IParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements IParticipantService {

    private final QuizCreationRepository quizCreationRepository;
    private final QuizQuestionCreationRepository quizQuestionCreationRepository;

    @Override
    public List<QuizCreation> findAllAvailableQuizzes() {
        return quizCreationRepository.findAll().stream()
                .filter(q -> q.getQuestions() != null && !q.getQuestions().isEmpty())
                .toList();
    }

    @Override
    public List<QuizQuestionCreation> findAllQuestionsByQuizId(long quizId) {
        QuizCreation quizCreation = quizCreationRepository.findById(quizId).orElseThrow(
                () -> new ResourceNotFoundException("Quiz not found!")
        );
        return quizQuestionCreationRepository.findByQuizCreation(quizCreation);
    }

    @Override
    public QuizCreation findQuizById(long quizId) {
        return quizCreationRepository.findById(quizId).orElseThrow(
                () -> new ResourceNotFoundException("Quiz not found!")
        );
    }
}
