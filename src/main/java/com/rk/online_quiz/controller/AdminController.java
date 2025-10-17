package com.rk.online_quiz.controller;

import com.rk.online_quiz.dto.Breadcrumb;
import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.service.IAdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IAdminService service;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("pageTitle", "Admin Dashboard");
        List<Breadcrumb> breadcrumbs = List.of(
//                new Breadcrumb("Home", null)
        );
        model.addAttribute("breadcrumbs", breadcrumbs);
        //Data
        List<QuizCreation> allQuizzes = service.findAllQuizzes();
        model.addAttribute("quizzes", allQuizzes);
        return "admin/dashboard";
    }


    @GetMapping("/create-quiz")
    public String createQuiz(Model model) {
        model.addAttribute("title", "Create Quiz");
        model.addAttribute("pageTitle", "Create New Quiz");
        List<Breadcrumb> breadcrumbs = List.of(
                new Breadcrumb("Quiz Creation", null)
        );
        model.addAttribute("breadcrumbs", breadcrumbs);

        model.addAttribute("quiz", new QuizCreationRequest());

        return "admin/quizCreation";
    }

    @PostMapping("/create-quiz")
    public String createQuiz(@Valid @ModelAttribute("quiz") QuizCreationRequest quiz,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Add Quiz");
            model.addAttribute("pageTitle", "Add New Quiz");

            List<Breadcrumb> breadcrumbs = List.of(
                    new Breadcrumb("Quiz Creation", null)
            );
            model.addAttribute("breadcrumbs", breadcrumbs);

            return "admin/quizCreation";
        }

        if (service.isQuizTitleAlreadyPresent(quiz.getTitle())) {
            result.reject("quizCreationError", "A quiz with this title already exists");
            model.addAttribute("title", "Add Quiz");
            model.addAttribute("pageTitle", "Add New Quiz");

            List<Breadcrumb> breadcrumbs = List.of(
                    new Breadcrumb("Quiz Creation", null)
            );
            model.addAttribute("breadcrumbs", breadcrumbs);

            return "admin/quizCreation";
        }
        //Create
        service.createNewQuiz(quiz);
        model.addAttribute("message", "Quiz created successfully");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/delete-quiz/{quizId}")
    public String deleteQuiz(Long quizId,
                             @PathVariable("quizId")
                             @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Invalid Quiz ID");
            return "admin/dashboard";
        }
        service.deleteQuiz(quizId);
        model.addAttribute("message", "Quiz deleted successfully");
        return "admin/dashboard";
    }

}
