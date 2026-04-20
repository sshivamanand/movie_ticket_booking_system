package com.cinema.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Movie entity - Scheduled for zero or more Shows.
 * Implements getMovieDetails() from class diagram.
 */
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    private String title;
    private String genre;
    private int duration; // in minutes

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Show> shows = new ArrayList<>();

    public Movie() {}

    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    public String getMovieDetails() {
        return String.format("Movie[id=%d, title='%s', genre='%s', duration=%d mins]", 
            movieId, title, genre, duration);
    }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public List<Show> getShows() { return shows; }
    public void setShows(List<Show> shows) { this.shows = shows; }
}
