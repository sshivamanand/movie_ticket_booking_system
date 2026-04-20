package com.cinema.controller;

import com.cinema.model.User;
import com.cinema.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                          @RequestParam String phone, @RequestParam String password,
                          RedirectAttributes ra) {
        try {
            userService.register(name, email, phone, password);
            ra.addFlashAttribute("message", "Registration successful. Please login.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                       HttpSession session, RedirectAttributes ra) {
        var user = userService.login(email, password);
        if (user.isEmpty()) {
            ra.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
        session.setAttribute("user", user.get());
        if ("ADMIN".equals(user.get().getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/movies";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
