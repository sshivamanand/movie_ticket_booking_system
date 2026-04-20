package com.cinema.repository;

import com.cinema.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByShowShowId(int showId);
    List<Seat> findByShowShowIdAndStatus(int showId, String status);
    Optional<Seat> findByShowShowIdAndSeatNumber(int showId, String seatNumber);
}
