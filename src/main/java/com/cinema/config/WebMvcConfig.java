package com.cinema.config;

import com.cinema.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
public class WebMvcConfig {

    @ControllerAdvice
    public static class GlobalModelAttribute {
        @ModelAttribute("user")
        public User addUserToModel(HttpServletRequest request) {
            HttpSession session = request.getSession(false);
            return session != null ? (User) session.getAttribute("user") : null;
        }
    }
}
