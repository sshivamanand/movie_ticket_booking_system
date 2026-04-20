package com.cinema.pattern.strategy;

import com.cinema.model.Payment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Context for Strategy Pattern - selects appropriate payment strategy.
 * Design Principle: Dependency Inversion - depends on PaymentStrategy abstraction.
 */
@Component
public class PaymentStrategyContext {
    private final Map<String, PaymentStrategy> strategies = new HashMap<>();

    public PaymentStrategyContext(CardPaymentStrategy card, UPIPaymentStrategy upi) {
        strategies.put("CARD", card);
        strategies.put("UPI", upi);
        strategies.put("NETBANKING", card); // Reuse card logic for demo
    }

    public boolean processPayment(Payment payment) {
        String method = payment.getPaymentMethod() != null ? payment.getPaymentMethod().toUpperCase() : "CARD";
        PaymentStrategy strategy = strategies.getOrDefault(method, strategies.get("CARD"));
        return strategy.process(payment);
    }
}
