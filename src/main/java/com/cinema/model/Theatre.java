package com.cinema.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Theatre entity - Represents a cinema theatre.
 * Contains one or more screens (1..* relationship).
 */
@Entity
@Table(name = "theatres")
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int theatreId;

    private String name;
    private String location;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screen> screens = new ArrayList<>();

    public Theatre() {}

    public Theatre(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public int getTheatreId() { return theatreId; }
    public void setTheatreId(int theatreId) { this.theatreId = theatreId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public List<Screen> getScreens() { return screens; }
    public void setScreens(List<Screen> screens) { this.screens = screens; }
}
