package com.rk.online_quiz.service.impl;

import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.dto.admin.request.QuizQuestionCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestOptionCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;
import com.rk.online_quiz.exception.ResourceNotFoundException;
import com.rk.online_quiz.repository.QuizCreationRepository;
import com.rk.online_quiz.repository.QuizQuestionCreationRepository;
import com.rk.online_quiz.service.IAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final QuizCreationRepository quizCreationRepository;
    private final QuizQuestionCreationRepository quizQuestionCreationRepository;

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
    public void updateQuiz(long id, String title, String description, int duration) {
        QuizCreation quiz = quizCreationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Invalid quiz update request")
        );
        quiz.setTitle(title);
        quiz.setDescription(description);
        quiz.setTimeLimitMinutes(duration);

        quizCreationRepository.save(quiz);
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

    @Override
    public QuizCreation findQuizById(Long id) {
        return quizCreationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Quiz not found")
        );
    }

    @Override
    public QuizQuestionCreation findQuizQuestionById(Long id) {
       return quizQuestionCreationRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Question not found"));
    }

    @Override
    public List<QuizQuestionCreation> findAllQuizQuestions() {
        return quizQuestionCreationRepository.findAll();
    }

    @Override
    @Transactional
    public void updateOrInsertQuizQuestion(Long quizId, QuizQuestionCreationRequest request) {
        QuizCreation quiz = quizCreationRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));

        QuizQuestionCreation question;

        // If question ID exists, try to update it
        if (request.getId() != null) {
            question = quizQuestionCreationRepository.findById(request.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

            // Update question text
            question.setQuestion(request.getQuestion());

            // Remove old options (orphanRemoval = true will delete them)
            question.getOptions().clear();
        } else {
            // Otherwise, create a new question
            question = new QuizQuestionCreation();
            question.setQuestion(request.getQuestion());
            question.setQuizCreation(quiz);
        }

        // Add new/updated options
        List<QuizQuestOptionCreation> updatedOptions = request.getOptions().stream()
                .map(o -> {
                    QuizQuestOptionCreation opt = new QuizQuestOptionCreation();
                    opt.setOption(o.getOption());
                    opt.setCorrect(o.isCorrect());
                    opt.setQuestion(question); // important for bidirectional mapping
                    return opt;
                })
                .toList();

        question.getOptions().addAll(updatedOptions);

        // Save (cascade handles options)
        quizQuestionCreationRepository.save(question);
    }

    @Override
    public void deleteQuizQuestion(Long questionId) {
        quizQuestionCreationRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        quizQuestionCreationRepository.deleteById(questionId);
    }

}
