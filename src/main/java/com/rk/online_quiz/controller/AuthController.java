package com.rk.online_quiz.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

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
}
