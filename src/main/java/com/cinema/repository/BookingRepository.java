package com.cinema.repository;

import com.cinema.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserUserId(int userId);
    List<Booking> findByShowShowId(int showId);
    List<Booking> findByStatus(String status);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :from AND :to AND b.status = 'CONFIRMED'")
    List<Booking> findConfirmedByDateRange(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
