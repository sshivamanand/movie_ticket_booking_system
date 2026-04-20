package com.cinema.pattern.singleton;

/**
 * Singleton Pattern (Creational): Single instance simulates external Payment Gateway.
 * Design Principle: Single Responsibility - only handles gateway communication.
 */
public final class PaymentGateway {
    private static volatile PaymentGateway instance;

    private PaymentGateway() {}

    public static PaymentGateway getInstance() {
        if (instance == null) {
            synchronized (PaymentGateway.class) {
                if (instance == null) {
                    instance = new PaymentGateway();
                }
            }
        }
        return instance;
    }

    public boolean authorizePayment(double amount, String method) {
        // Simulate external Payment Gateway API call
        return amount > 0;
    }
}
