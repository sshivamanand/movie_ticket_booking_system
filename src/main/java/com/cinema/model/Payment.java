package com.cinema.model;

import jakarta.persistence.*;

/**
 * Payment entity - Associated with exactly one Booking.
 * Implements processPayment() - integrates with Payment Gateway (supporting actor).
 */
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private double amount;
    private String paymentMethod; // CARD, UPI, NETBANKING, CASH
    private String paymentStatus = "PENDING"; // PENDING, SUCCESS, FAILED

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    public Payment() {}

    public Payment(double amount, String paymentMethod, Booking booking) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
    }

    /**
     * Simulates payment processing with external Payment Gateway.
     * In production, this would integrate with actual payment provider.
     */
    public boolean processPayment() {
        // Simulate Payment Gateway response
        this.paymentStatus = "SUCCESS";
        return true;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }
}
