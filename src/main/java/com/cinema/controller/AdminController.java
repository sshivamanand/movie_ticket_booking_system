package com.cinema.controller;

import com.cinema.model.User;
import com.cinema.service.AdminMovieService;
import com.cinema.service.AdminShowService;
import com.cinema.service.ReportService;
import com.cinema.repository.ScreenRepository;
import com.cinema.service.TheatreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final TheatreService theatreService;
    private final AdminShowService adminShowService;
    private final AdminMovieService adminMovieService;
    private final ReportService reportService;
    private final ScreenRepository screenRepository;

    public AdminController(TheatreService theatreService, AdminShowService adminShowService,
                          AdminMovieService adminMovieService, ReportService reportService,
                          ScreenRepository screenRepository) {
        this.theatreService = theatreService;
        this.adminShowService = adminShowService;
        this.adminMovieService = adminMovieService;
        this.reportService = reportService;
        this.screenRepository = screenRepository;
    }

    private boolean checkAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping
    public String adminHome(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/index";
    }

    @GetMapping("/theatres")
    public String manageTheatres(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";
        model.addAttribute("theatres", theatreService.findAll());
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/theatres";
    }

    @PostMapping("/theatres")
    public String addTheatre(@RequestParam String name, @RequestParam String location,
                            RedirectAttributes ra) {
        theatreService.addTheatre(name, location);
        ra.addFlashAttribute("message", "Theatre added.");
        return "redirect:/admin/theatres";
    }

    @PostMapping("/theatres/{id}/screens")
    public String addScreen(@PathVariable int id, @RequestParam int screenNumber, @RequestParam int totalSeats,
                           RedirectAttributes ra) {
        theatreService.addScreen(id, screenNumber, totalSeats);
        ra.addFlashAttribute("message", "Screen added.");
        return "redirect:/admin/theatres";
    }

    @GetMapping("/movies")
    public String manageMovies(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";
        model.addAttribute("movies", adminMovieService.getAllMovies());
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/movies";
    }

    @PostMapping("/movies")
    public String addMovie(@RequestParam String title, @RequestParam String genre, @RequestParam int duration,
                          RedirectAttributes ra) {
        adminMovieService.addMovie(title, genre, duration);
        ra.addFlashAttribute("message", "Movie added.");
        return "redirect:/admin/movies";
    }

    @PostMapping("/movies/{id}")
    public String updateMovie(@PathVariable int id, @RequestParam String title,
                             @RequestParam String genre, @RequestParam int duration, RedirectAttributes ra) {
        adminMovieService.updateMovie(id, title, genre, duration);
        ra.addFlashAttribute("message", "Movie updated.");
        return "redirect:/admin/movies";
    }

    @GetMapping("/shows")
    public String manageShows(HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";
        model.addAttribute("shows", adminShowService.getAllShows());
        model.addAttribute("movies", adminMovieService.getAllMovies());
        model.addAttribute("theatres", theatreService.findAll());
        model.addAttribute("screens", screenRepository.findAll());
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/shows";
    }

    @PostMapping("/shows")
    public String addShow(@RequestParam int movieId, @RequestParam int screenId,
                         @RequestParam String showTime, @RequestParam double price,
                         RedirectAttributes ra) {
        adminShowService.addShow(movieId, screenId, java.time.LocalDateTime.parse(showTime), price);
        ra.addFlashAttribute("message", "Show added.");
        return "redirect:/admin/shows";
    }

    @GetMapping("/reports")
    public String viewReports(@RequestParam(required = false) String from, @RequestParam(required = false) String to,
                             HttpSession session, Model model) {
        if (!checkAdmin(session)) return "redirect:/login";
        LocalDate fromDate = from != null && !from.isBlank() ? LocalDate.parse(from) : LocalDate.now().minusMonths(1);
        LocalDate toDate = to != null && !to.isBlank() ? LocalDate.parse(to) : LocalDate.now();
        model.addAttribute("report", reportService.getSalesReport(fromDate, toDate));
        model.addAttribute("fromDate", fromDate.toString());
        model.addAttribute("toDate", toDate.toString());
        model.addAttribute("user", session.getAttribute("user"));
        return "admin/reports";
    }
}
