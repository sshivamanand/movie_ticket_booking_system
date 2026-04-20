package com.cinema.service;

import com.cinema.model.Show;
import com.cinema.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public List<Show> getShowTimings(int movieId) {
        LocalDateTime from = LocalDate.now().atStartOfDay();
        return showRepository.findShowsByMovieAndDate(movieId, from);
    }

    public List<Show> getShowsByMovie(int movieId) {
        return showRepository.findByMovieMovieId(movieId);
    }

    public Optional<Show> findById(int id) {
        return showRepository.findById(id);
    }

    public Optional<Show> findByIdWithDetails(int id) {
        return showRepository.findByIdWithDetails(id);
    }
}
