package com.cinema.pattern.factory;

import com.cinema.model.Payment;
import com.cinema.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Factory Pattern (Creational): Creates Payment instances dynamically.
 */
@Component
public class PaymentFactory {
    public Payment createPayment(double amount, String method, Booking booking) {
        return new Payment(amount, method, booking);
    }
}
