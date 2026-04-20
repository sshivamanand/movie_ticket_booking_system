package com.cinema.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Screen entity - Belongs to one Theatre, hosts zero or more Shows.
 */
@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screenId;

    private int screenNumber;
    private int totalSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Show> shows = new ArrayList<>();

    public Screen() {}

    public Screen(int screenNumber, int totalSeats, Theatre theatre) {
        this.screenNumber = screenNumber;
        this.totalSeats = totalSeats;
        this.theatre = theatre;
    }

    public int getScreenId() { return screenId; }
    public void setScreenId(int screenId) { this.screenId = screenId; }
    public int getScreenNumber() { return screenNumber; }
    public void setScreenNumber(int screenNumber) { this.screenNumber = screenNumber; }
    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public Theatre getTheatre() { return theatre; }
    public void setTheatre(Theatre theatre) { this.theatre = theatre; }
    public List<Show> getShows() { return shows; }
    public void setShows(List<Show> shows) { this.shows = shows; }
}
