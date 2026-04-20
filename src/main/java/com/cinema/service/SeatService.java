package com.cinema.service;

import com.cinema.model.Seat;
import com.cinema.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seat> getSeatsByShow(int showId) {
        return seatRepository.findByShowShowId(showId);
    }

    public List<Seat> getAvailableSeats(int showId) {
        return seatRepository.findByShowShowIdAndStatus(showId, "AVAILABLE");
    }
}
