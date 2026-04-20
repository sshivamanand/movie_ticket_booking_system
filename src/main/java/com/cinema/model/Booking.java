package com.cinema.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Booking entity - Made by one User, for one Show.
 * Reserves one or more Seats, has one Payment.
 * Status: PENDING, CONFIRMED, CANCELLED
 */
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    private LocalDate bookingDate;
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(mappedBy = "booking", fetch = FetchType.EAGER)
    private List<Seat> seats = new ArrayList<>();

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

    public Booking() {}

    public Booking(User user, Show show, LocalDate bookingDate) {
        this.user = user;
        this.show = show;
        this.bookingDate = bookingDate;
    }

    public void confirmBooking() {
        this.status = "CONFIRMED";
    }

    public void cancelBooking() {
        this.status = "CANCELLED";
        for (Seat seat : seats) {
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
        }
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment payment) { this.payment = payment; }
}
