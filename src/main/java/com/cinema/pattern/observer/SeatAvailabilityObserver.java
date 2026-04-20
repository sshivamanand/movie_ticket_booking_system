package com.cinema.pattern.observer;

import com.cinema.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SeatAvailabilityObserver implements BookingObserver {
    private static final Logger log = LoggerFactory.getLogger(SeatAvailabilityObserver.class);

    @Override
    public void onBookingConfirmed(Booking booking) {
        log.info("Booking {} confirmed - {} seat(s) reserved for show {}", 
            booking.getBookingId(), 
            booking.getSeats().size(), 
            booking.getShow().getShowId());
    }
}
