package com.cinema.controller;

import com.cinema.model.User;
import com.cinema.service.MovieService;
import com.cinema.service.ShowService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final ShowService showService;

    public MovieController(MovieService movieService, ShowService showService) {
        this.movieService = movieService;
        this.showService = showService;
    }

    @GetMapping("/movies")
    public String searchMovies(@RequestParam(required = false) String q, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        var movies = q == null || q.isBlank() ? movieService.findAll() : movieService.searchMovies(q);
        model.addAttribute("movies", movies);
        model.addAttribute("user", user);
        model.addAttribute("query", q != null ? q : "");
        return "movies";
    }

    @GetMapping("/movies/{id}/shows")
    public String viewShowTimings(@PathVariable int id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        var movie = movieService.findById(id);
        if (movie.isEmpty()) return "redirect:/movies";

        var shows = showService.getShowTimings(id);
        model.addAttribute("movie", movie.get());
        model.addAttribute("shows", shows);
        model.addAttribute("user", user);
        return "show-timings";
    }
}
