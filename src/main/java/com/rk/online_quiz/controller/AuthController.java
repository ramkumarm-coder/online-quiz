package com.rk.online_quiz.controller;

import com.rk.online_quiz.dto.register.RegisterUserRequest;
import com.rk.online_quiz.entity.LoginUser;
import com.rk.online_quiz.enums.UserRoles;
import com.rk.online_quiz.repository.LoginUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final LoginUserRepository userRepository;

    @GetMapping({"/", "/index"})
    public String index(){ return "redirect:/login"; }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                            Authentication auth,
                            Model model) {
        model.addAttribute("title", "Login");
        // If user is already logged in, redirect based on their role
        if (auth != null && auth.isAuthenticated()) {
            String role = auth.getAuthorities().iterator().next().getAuthority();
            return switch (role) {
                case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
                case "ROLE_PARTICIPANT" -> "redirect:/participant/dashboard";
                default -> "redirect:/logout";
            };
        }

        if (error != null) {
            model.addAttribute("error", "Invalid credentials");
        }

        return "login";
    }

    @GetMapping("/default")
    public String redirectAfterLogin(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();
        return switch (role) {
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            case "ROLE_PARTICIPANT" -> "redirect:/participant/dashboard";
            default -> "redirect:/login";
        };
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterUserRequest());
        model.addAttribute("pageTitle", "User Registration");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerUser") RegisterUserRequest form,
                               BindingResult result,
                               Model model) {

        // Form validation
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.registerUser", "Passwords do not match");
        }

        if (userRepository.findByUsername(form.getUsername()).isPresent()) {
            result.rejectValue("username", "error.registerUser", "Email already registered");
        }

        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "User Registration");
            return "register";
        }

        // Save user
        LoginUser user = new LoginUser();
        user.setName(form.getName());
        user.setUsername(form.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(UserRoles.PARTICIPANT);
        user.setActive(true);

        userRepository.save(user);

        model.addAttribute("successMessage", "Registration successful! You can now log in.");
        return "redirect:/login";
    }
}
