package com.project.videotheque.service;

import com.project.videotheque.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();
    Optional<Movie> findById(Long id);
    Movie create(Movie m);
    Movie update(Long id, Movie m);
    void delete(Long id);

    Page<Movie> search(String title, Integer year, Pageable pageable);
}
