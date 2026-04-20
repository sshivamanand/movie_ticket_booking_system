package com.cinema.repository;

import com.cinema.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer> {
    List<Screen> findByTheatreTheatreId(int theatreId);
}
