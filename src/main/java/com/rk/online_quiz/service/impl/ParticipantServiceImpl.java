package com.rk.online_quiz.service.impl;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.repository.QuizCreationRepository;
import com.rk.online_quiz.service.IParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements IParticipantService {

    private final QuizCreationRepository quizCreationRepository;

    @Override
    public List<QuizCreation> findAllAvailableQuizzes() {
        return quizCreationRepository.findAll().stream()
                .filter(q -> q.getQuestions() != null && !q.getQuestions().isEmpty())
                .toList();
    }
}
