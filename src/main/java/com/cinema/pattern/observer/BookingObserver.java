package com.cinema.pattern.observer;

import com.cinema.model.Booking;

/**
 * Observer Pattern (Behavioral): Interface for observers notified on booking events.
 * Design Principle: Open/Closed - new observers can be added without changing subject.
 */
public interface BookingObserver {
    void onBookingConfirmed(Booking booking);
}
