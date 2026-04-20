package com.cinema.pattern.observer;

import com.cinema.model.Booking;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern - Subject that notifies observers on booking confirmation.
 */
public class BookingSubject {
    private final List<BookingObserver> observers = new ArrayList<>();

    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    public void notifyBookingConfirmed(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.onBookingConfirmed(booking);
        }
    }
}
