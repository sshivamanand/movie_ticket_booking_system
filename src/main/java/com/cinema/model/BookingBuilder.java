package com.cinema.model;

import java.time.LocalDate;
import java.util.List;

public class BookingBuilder {
    private User user;
    private Show show;
    private LocalDate bookingDate;
    private String status = "PENDING";
    private List<Seat> seats;
    private Payment payment;

    public BookingBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public BookingBuilder setShow(Show show) {
        this.show = show;
        return this;
    }

    public BookingBuilder setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public BookingBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public BookingBuilder setSeats(List<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public BookingBuilder setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    public Booking build() {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingDate(bookingDate);
        booking.setStatus(status);
        booking.setSeats(seats);
        booking.setPayment(payment);
        return booking;
    }
}
