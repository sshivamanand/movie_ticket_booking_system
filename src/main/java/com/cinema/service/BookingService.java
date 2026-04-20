package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.model.User;
import com.cinema.pattern.facade.BookingFacade;
import com.cinema.model.Seat;
import com.cinema.repository.BookingRepository;
import com.cinema.repository.ShowRepository;
import com.cinema.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final BookingFacade bookingFacade;

    public BookingService(BookingRepository bookingRepository, ShowRepository showRepository,
                          SeatRepository seatRepository, BookingFacade bookingFacade) {
        this.bookingRepository = bookingRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.bookingFacade = bookingFacade;
    }

    public BookingFacade.BookingResult bookTickets(User user, int showId, List<String> seatNumbers, String paymentMethod) {
        var showOpt = showRepository.findById(showId);
        if (showOpt.isEmpty()) {
            return BookingFacade.BookingResult.error("Show not found");
        }
        return bookingFacade.bookTickets(user, showOpt.get(), seatNumbers, paymentMethod);
    }

    public List<Booking> getBookingsByUser(int userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    public Optional<Booking> getBookingById(int id) {
        return bookingRepository.findById(id);
    }

    @Transactional
    public boolean cancelBooking(int bookingId, int userId) {
        Optional<Booking> opt = bookingRepository.findById(bookingId);
        if (opt.isEmpty() || opt.get().getUser().getUserId() != userId) {
            return false;
        }
        Booking booking = opt.get();
        if ("CANCELLED".equals(booking.getStatus())) {
            return false;
        }
        for (Seat seat : booking.getSeats()) {
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
            seatRepository.save(seat);
        }
        booking.setStatus("CANCELLED");
        booking.getSeats().clear();
        bookingRepository.save(booking);
        return true;
    }
}
