package com.cinema.controller;

import com.cinema.dto.SeatInfo;
import com.cinema.model.User;
import com.cinema.service.BookingService;
import com.cinema.service.SeatService;
import com.cinema.service.ShowService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookingController {
    private final BookingService bookingService;
    private final ShowService showService;
    private final SeatService seatService;

    public BookingController(BookingService bookingService, ShowService showService, SeatService seatService) {
        this.bookingService = bookingService;
        this.showService = showService;
        this.seatService = seatService;
    }

    @GetMapping("/book/{showId}")
    public String bookForm(@PathVariable int showId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        var show = showService.findByIdWithDetails(showId);
        if (show.isEmpty()) return "redirect:/movies";

        var seats = seatService.getSeatsByShow(showId).stream()
            .map(s -> new SeatInfo(s.getSeatId(), s.getSeatNumber(), s.getSeatType(), s.getStatus()))
            .toList();

        model.addAttribute("show", show.get());
        model.addAttribute("seats", seats);
        try {
            model.addAttribute("seatsJson", new ObjectMapper().writeValueAsString(seats));
        } catch (JsonProcessingException e) {
            model.addAttribute("seatsJson", "[]");
        }
        model.addAttribute("user", user);
        return "book-ticket";
    }

    @PostMapping("/book")
    public String bookTickets(@RequestParam int showId, @RequestParam List<String> seatNumbers,
                             @RequestParam(defaultValue = "CARD") String paymentMethod,
                             HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        var result = bookingService.bookTickets(user, showId, seatNumbers, paymentMethod);
        if (result.isSuccess()) {
            ra.addFlashAttribute("message", "Booking confirmed! ID: " + result.getBooking().getBookingId());
            return "redirect:/bookings";
        }
        ra.addFlashAttribute("error", result.getMessage());
        return "redirect:/book/" + showId;
    }

    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        var bookings = bookingService.getBookingsByUser(user.getUserId());
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);
        return "bookings";
    }

    @PostMapping("/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable int id, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        if (bookingService.cancelBooking(id, user.getUserId())) {
            ra.addFlashAttribute("message", "Booking cancelled successfully.");
        } else {
            ra.addFlashAttribute("error", "Unable to cancel booking.");
        }
        return "redirect:/bookings";
    }
}
