package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminMovieService {
    private final MovieRepository movieRepository;

    public AdminMovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie addMovie(String title, String genre, int duration) {
        Movie movie = new Movie(title, genre, duration);
        return movieRepository.save(movie);
    }

    public Movie updateMovie(int id, String title, String genre, Integer duration) {
        Movie m = movieRepository.findById(id).orElseThrow();
        if (title != null) m.setTitle(title);
        if (genre != null) m.setGenre(genre);
        if (duration != null) m.setDuration(duration);
        return movieRepository.save(m);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}
