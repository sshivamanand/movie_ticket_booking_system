package com.cinema.repository;

import com.cinema.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
    List<Show> findByMovieMovieId(int movieId);
    List<Show> findByScreenScreenId(int screenId);
    List<Show> findByShowTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Show s WHERE s.movie.movieId = :movieId AND s.showTime >= :from ORDER BY s.showTime")
    List<Show> findShowsByMovieAndDate(@Param("movieId") int movieId, @Param("from") LocalDateTime from);

    @Query("SELECT s FROM Show s LEFT JOIN FETCH s.movie LEFT JOIN FETCH s.screen sc LEFT JOIN FETCH sc.theatre WHERE s.showId = :id")
    java.util.Optional<Show> findByIdWithDetails(@Param("id") int id);
}
