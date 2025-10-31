package com.rk.online_quiz.controller;

import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;
import com.rk.online_quiz.service.IParticipantService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/participant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PARTICIPANT')")
public class ParticipantController {

    private final IParticipantService participantService;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("title", "Dashboard");
        //Available quiz
        List<QuizCreation> allAvailableQuizzes = participantService.findAllAvailableQuizzes();
        model.addAttribute("quizzes", allAvailableQuizzes);
        return "participant/dashboard";
    }

    @PostMapping("/attend-quiz/{quizId}")
    public String attendQuiz(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,
            Model model) {

        model.addAttribute("title", "Attend Quiz");
        //Available quiz
        List<QuizQuestionCreation> allQuestionsByQuizId = participantService.findAllQuestionsByQuizId(quizId);
        QuizCreation quizById = participantService.findQuizById(quizId);
        model.addAttribute("questions", allQuestionsByQuizId);
        model.addAttribute("timer", quizById.getTimeLimitMinutes());
        return "participant/dashboard";
    }


    @PostMapping("/submit-quiz/{quizId}")
    public String submitQuiz(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,

            Model model) {

        model.addAttribute("title", "Attend Quiz");
        //Available quiz
        List<QuizQuestionCreation> allQuestionsByQuizId = participantService.findAllQuestionsByQuizId(quizId);
        QuizCreation quizById = participantService.findQuizById(quizId);
        model.addAttribute("questions", allQuestionsByQuizId);
        model.addAttribute("timer", quizById.getTimeLimitMinutes());
        return "participant/dashboard";
    }
}
