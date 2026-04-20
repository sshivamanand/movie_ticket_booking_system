package com.cinema.repository;

import com.cinema.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Integer> {
    List<Theatre> findByLocationContainingIgnoreCase(String location);
    List<Theatre> findByNameContainingIgnoreCase(String name);

    @Query("SELECT t FROM Theatre t LEFT JOIN FETCH t.screens ORDER BY t.name")
    List<Theatre> findAllWithScreens();
}
