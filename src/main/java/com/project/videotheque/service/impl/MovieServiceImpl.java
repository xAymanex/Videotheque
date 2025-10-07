package com.project.videotheque.service.impl;

import com.project.videotheque.model.Movie;
import com.project.videotheque.repository.MovieRepository;
import com.project.videotheque.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service @Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repo;
    public MovieServiceImpl(MovieRepository repo) { this.repo = repo; }

    @Override public List<Movie> findAll() { return repo.findAll(); }
    @Override public Optional<Movie> findById(Long id) { return repo.findById(id); }
    @Override public Movie create(Movie m) { return repo.save(m); }

    @Override
    public Movie update(Long id, Movie m) {
        return repo.findById(id).map(e -> {
            e.setTitle(m.getTitle());
            e.setYear(m.getYear());
            e.setDurationMinutes(m.getDurationMinutes());
            e.setDirector(m.getDirector());
            e.setGenre(m.getGenre());        // ManyToOne
            e.setActors(m.getActors());      // ManyToMany
            return repo.save(e);
        }).orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id));
    }

    @Override public void delete(Long id) { repo.deleteById(id); }
    @Override public Page<Movie> search(String title, Integer year, Pageable pageable) {
        return repo.search(title, year, pageable);
    }
}
