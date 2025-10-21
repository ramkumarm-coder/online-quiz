package com.rk.online_quiz.controller;

import com.rk.online_quiz.dto.Breadcrumb;
import com.rk.online_quiz.dto.admin.request.QuizCreationRequest;
import com.rk.online_quiz.dto.admin.request.QuizQuestionCreationRequest;
import com.rk.online_quiz.entity.QuizCreation;
import com.rk.online_quiz.entity.QuizQuestionCreation;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final IAdminService service;

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("pageTitle", "Admin Dashboard");
        List<Breadcrumb> breadcrumbs = List.of(
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
    public String deleteQuiz(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,
            Model model) {
        service.deleteQuiz(quizId);
        model.addAttribute("message", "Quiz deleted successfully");
        return "admin/dashboard";
    }

    //Edit Quiz
    @GetMapping("/edit-quiz/{quizId}")
    public String editQuiz(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,
            Model model) {
        model.addAttribute("title", "Edit Quiz");
        model.addAttribute("pageTitle", "Edit Quiz");
        List<Breadcrumb> breadcrumbs = List.of(
//                new Breadcrumb("Edit Quiz", "/admin/dashboard"),
                new Breadcrumb("Edit Quiz", null)
        );
        model.addAttribute("breadcrumbs", breadcrumbs);

        QuizCreation quizById = service.findQuizById(quizId);
        List<QuizQuestionCreation> allQuizQuestions = service.findAllQuizQuestions();

        model.addAttribute("quiz", quizById);
        model.addAttribute("quizQuestions", allQuizQuestions);
        return "admin/editQuiz";
    }

    @PostMapping("/update-quiz")
    public String updateQuiz(@Valid @ModelAttribute("quiz") QuizCreationRequest quiz,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            System.out.println("Error**********");
            model.addAttribute("title", "Edit Quiz");
            model.addAttribute("pageTitle", "Edit Quiz");
            List<Breadcrumb> breadcrumbs = List.of(
                    new Breadcrumb("Edit Quiz", null)
            );
            model.addAttribute("breadcrumbs", breadcrumbs);
            return "admin/edit-quiz/" + quiz.getId();
        }

        System.out.println("Executed************");
        //Update
        service.updateQuiz(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getTimeLimitMinutes());
        model.addAttribute("message", "Quiz updated successfully");
        return "redirect:/admin/edit-quiz/" + quiz.getId();
    }

    @GetMapping("/add-quiz-question/{quizId}")
    public String addQuizQuestion(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,
            Model model) {

        model.addAttribute("title", "Create Quiz Question");
        model.addAttribute("pageTitle", "Create New Question");
        List<Breadcrumb> breadcrumbs = List.of(
                new Breadcrumb("Edit Quiz", "/admin/edit-quiz/" + quizId),
                new Breadcrumb("Create Quiz Question", null)
        );
        model.addAttribute("breadcrumbs", breadcrumbs);

        model.addAttribute("question", new QuizQuestionCreationRequest());
        model.addAttribute("quizId", quizId);
        return "admin/add-question";
    }


    @GetMapping("/update-quiz-question/{questionId}")
    public String updateQuizQuestion(
            @PathVariable("questionId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long questionId,
            Model model) {

        QuizQuestionCreation quizQuestionById = service.findQuizQuestionById(questionId);
        long quizId = quizQuestionById.getQuizCreation().getId();

        model.addAttribute("title", "Create Quiz Question");
        model.addAttribute("pageTitle", "Create New Question");
        List<Breadcrumb> breadcrumbs = List.of(
                new Breadcrumb("Edit Quiz", "/admin/edit-quiz/" + quizId),
                new Breadcrumb("Update Quiz Question", null)
        );
        model.addAttribute("breadcrumbs", breadcrumbs);
        QuizQuestionCreation question = service.findQuizQuestionById(questionId);
        model.addAttribute("question", question);
        model.addAttribute("quizId", quizId);
        return "admin/add-question";
    }


    @PostMapping("/update-quiz-question/{quizId}")
    public String updateQuizQuestion(
            @PathVariable("quizId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long quizId,
            @Valid @ModelAttribute("question")
            QuizQuestionCreationRequest request,
            Model model,
            RedirectAttributes redirectAttributes) {

        System.out.println("quizId *************: " +quizId);
        service.updateOrInsertQuizQuestion(quizId, request);
        redirectAttributes.addFlashAttribute("message", "Quiz Question modified successfully");

        return "redirect:/admin/edit-quiz/" + quizId;
    }

    @GetMapping("/delete-quiz-question/{questionId}")
    public String deleteQuizQuestion(
            @PathVariable("questionId")
            @Min(value = 1, message = "Invalid quiz ID. Must be greater than 0.")
            Long questionId,
            Model model,
            RedirectAttributes redirectAttributes) {

        service.deleteQuizQuestion(questionId);
        redirectAttributes.addFlashAttribute("message", "Quiz Question deleted successfully");
        return "redirect:/admin/edit-quiz/" + questionId;
    }
}
