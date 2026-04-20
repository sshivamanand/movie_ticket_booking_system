package com.cinema.model;

import jakarta.persistence.*;

/**
 * Seat entity - Included in one Show.
 * Can be reserved by a Booking.
 * Status: AVAILABLE, RESERVED, BOOKED
 */
@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    private String seatNumber;
    private String seatType; // e.g., NORMAL, PREMIUM, RECLINER
    private String status = "AVAILABLE"; // AVAILABLE, RESERVED, BOOKED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Seat() {}

    public Seat(String seatNumber, String seatType, Show show) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.show = show;
    }

    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }
    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Show getShow() { return show; }
    public void setShow(Show show) { this.show = show; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
