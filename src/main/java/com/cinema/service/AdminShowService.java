package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Screen;
import com.cinema.model.Seat;
import com.cinema.model.Show;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.ScreenRepository;
import com.cinema.repository.ShowRepository;
import com.cinema.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    public AdminShowService(ShowRepository showRepository, MovieRepository movieRepository,
                           ScreenRepository screenRepository, SeatRepository seatRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.seatRepository = seatRepository;
    }

    public Show addShow(int movieId, int screenId, LocalDateTime showTime, double price) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Screen screen = screenRepository.findById(screenId).orElseThrow();
        Show show = new Show(showTime, price, movie, screen);
        show = showRepository.save(show);
        createSeatsForShow(show, screen.getTotalSeats());
        return show;
    }

    private void createSeatsForShow(Show show, int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            String type = i <= totalSeats / 3 ? "PREMIUM" : (i <= 2 * totalSeats / 3 ? "NORMAL" : "RECLINER");
            Seat seat = new Seat("S" + i, type, show);
            seatRepository.save(seat);
            show.getSeats().add(seat);
        }
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<Show> findById(int id) {
        return showRepository.findById(id);
    }
}
