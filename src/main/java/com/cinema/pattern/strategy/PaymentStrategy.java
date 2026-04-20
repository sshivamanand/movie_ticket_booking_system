package com.cinema.pattern.strategy;

import com.cinema.model.Payment;

/**
 * Strategy Pattern (Behavioral): Defines family of payment algorithms.
 * Design Principle: Open/Closed - New payment methods can be added without modifying existing code.
 */
public interface PaymentStrategy {
    boolean process(Payment payment);
}
