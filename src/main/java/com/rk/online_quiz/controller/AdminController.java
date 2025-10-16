package com.rk.online_quiz.controller;

import com.rk.online_quiz.dto.Breadcrumb;
import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.service.IAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "admin/quiz-creation";
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

            return "admin/quiz-creation";
        }

        if ("Duplicate Quiz".equalsIgnoreCase(quiz.getTitle())) {
            result.reject("quizCreationError", "A quiz with this title already exists");
            model.addAttribute("title", "Add Quiz");
            model.addAttribute("pageTitle", "Add New Quiz");

            List<Breadcrumb> breadcrumbs = List.of(
                    new Breadcrumb("Quiz Creation", null)
            );
            model.addAttribute("breadcrumbs", breadcrumbs);

            return "admin/quiz-creation";
        }
        //Create
        service.createNewQuiz(quiz);
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/quiz/add")
    public String showAddQuizForm(Model model) {
        model.addAttribute("title", "Add New Quiz");
        model.addAttribute("pageTitle", "Add Quiz");

        model.addAttribute("breadcrumbs", List.of(
                new Breadcrumb("Admin", "/admin/dashboard"),
                new Breadcrumb("Quizzes", "/admin/quizzes"),
                new Breadcrumb("Add Quiz", null)
        ));

        model.addAttribute("quiz", new QuizCreationRequest());
        return "admin/add-quiz";
    }

    @PostMapping("/quiz/add")
    public String addQuiz(@Valid @ModelAttribute("quiz") QuizCreationRequest quiz,
                          BindingResult result,
                          Model model) {

        // Example: server-side validation
        if ("Duplicate Quiz".equalsIgnoreCase(quiz.getTitle())) {
            result.reject("quizCreationError", "A quiz with this title already exists");
        }

        if (result.hasErrors()) {
            model.addAttribute("title", "Add Quiz");
            return "admin/add-quiz"; // re-render form with errors
        }

        // Save logic here
        return "redirect:/admin/dashboard";
    }

}
