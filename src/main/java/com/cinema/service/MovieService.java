package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> searchMovies(String query) {
        if (query == null || query.isBlank()) {
            return movieRepository.findAll();
        }
        List<Movie> byTitle = movieRepository.findByTitleContainingIgnoreCase(query);
        List<Movie> byGenre = movieRepository.findByGenreContainingIgnoreCase(query);
        return byTitle.isEmpty() ? byGenre : byTitle;
    }

    public Optional<Movie> findById(int id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
