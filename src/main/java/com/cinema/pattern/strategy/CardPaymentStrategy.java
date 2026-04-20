package com.cinema.pattern.strategy;

import com.cinema.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean process(Payment payment) {
        // Simulate card payment via Payment Gateway
        payment.setPaymentStatus("SUCCESS");
        return true;
    }
}
