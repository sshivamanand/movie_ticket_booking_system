package com.cinema.controller;

import com.cinema.model.User;
import com.cinema.service.TheatreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/theatres")
    public String listTheatres(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("theatres", theatreService.findAllWithScreens());
        model.addAttribute("user", user);
        return "theatres";
    }
}
