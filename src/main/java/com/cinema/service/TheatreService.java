package com.cinema.service;

import com.cinema.model.Screen;
import com.cinema.model.Theatre;
import com.cinema.repository.TheatreRepository;
import com.cinema.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;

    public TheatreService(TheatreRepository theatreRepository, ScreenRepository screenRepository) {
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
    }

    public List<Theatre> findAll() {
        return theatreRepository.findAll();
    }

    public List<Theatre> findAllWithScreens() {
        return theatreRepository.findAllWithScreens();
    }

    public Optional<Theatre> findById(int id) {
        return theatreRepository.findById(id);
    }

    public Theatre addTheatre(String name, String location) {
        Theatre theatre = new Theatre(name, location);
        return theatreRepository.save(theatre);
    }

    public Theatre updateTheatre(int id, String name, String location) {
        Theatre t = theatreRepository.findById(id).orElseThrow();
        if (name != null) t.setName(name);
        if (location != null) t.setLocation(location);
        return theatreRepository.save(t);
    }

    public Screen addScreen(int theatreId, int screenNumber, int totalSeats) {
        Theatre theatre = theatreRepository.findById(theatreId).orElseThrow();
        Screen screen = new Screen(screenNumber, totalSeats, theatre);
        return screenRepository.save(screen);
    }
}
