package com.cinema.pattern.facade;

import com.cinema.model.*;
import com.cinema.pattern.factory.PaymentFactory;
import com.cinema.repository.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade Pattern (Structural): Simplifies the complex booking process.
 */
@Component
public class BookingFacade {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentFactory paymentFactory;

    public BookingFacade(BookingRepository bookingRepository, SeatRepository seatRepository,
                         PaymentRepository paymentRepository, PaymentFactory paymentFactory) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.paymentRepository = paymentRepository;
        this.paymentFactory = paymentFactory;
    }

    @Transactional
    public BookingResult bookTickets(User user, Show show, List<String> seatNumbers, String paymentMethod) {
        List<Seat> seats = new ArrayList<>();
        for (String sn : seatNumbers) {
            Seat seat = seatRepository.findByShowShowIdAndSeatNumber(show.getShowId(), sn).orElseThrow();
            if (!"AVAILABLE".equals(seat.getStatus())) {
                return BookingResult.error("Seat " + sn + " is not available");
            }
            seat.setStatus("RESERVED");
            seats.add(seat);
        }

    // Builder Pattern usage
    Booking booking = new com.cinema.model.BookingBuilder()
        .setUser(user)
        .setShow(show)
        .setBookingDate(LocalDate.now())
        .setSeats(seats)
        .setStatus("CONFIRMED")
        .build();
    booking = bookingRepository.save(booking);

    // Set the booking for each seat and save
    for (Seat seat : seats) {
        seat.setBooking(booking);
        seatRepository.save(seat);
    }

    Payment payment = paymentFactory.createPayment(show.getPrice() * seats.size(), paymentMethod, booking);
    payment.processPayment();
    paymentRepository.save(payment);

    booking.setPayment(payment);
    bookingRepository.save(booking);

    return BookingResult.success(booking);
    }

    public static class BookingResult {
        private final boolean success;
        private final String message;
        private final Booking booking;

        private BookingResult(boolean success, String message, Booking booking) {
            this.success = success;
            this.message = message;
            this.booking = booking;
        }

        public static BookingResult success(Booking booking) {
            return new BookingResult(true, null, booking);
        }

        public static BookingResult error(String message) {
            return new BookingResult(false, message, null);
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Booking getBooking() {
            return booking;
        }
    }
}
