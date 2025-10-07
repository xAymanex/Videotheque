package com.project.videotheque.controller;

import com.project.videotheque.dto.MovieRequest;
import com.project.videotheque.model.*;
import com.project.videotheque.repository.ActorRepository;
import com.project.videotheque.repository.DirectorRepository;
import com.project.videotheque.repository.GenreRepository;
import com.project.videotheque.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.*;
import java.util.*;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;
    private final DirectorRepository directors;
    private final ActorRepository actors;
    private final GenreRepository genres;

    public MovieController(MovieService service,
                           DirectorRepository directors,
                           ActorRepository actors,
                           GenreRepository genres) {
        this.service = service;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
    }

    // ---- CRUD de base ----
    @GetMapping
    public List<Movie> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> byId(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieRequest req) {
        Movie m = toEntity(req, new Movie());
        var saved = service.create(m);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody @Valid MovieRequest req) {
        Movie m = toEntity(req, new Movie());
        return service.update(id, m);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ---- Recherche + pagination ----


    @GetMapping("/search")
    public Page<Movie> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> sort // ex: ["title,asc","year,desc"]
    ){
        Sort springSort = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            List<Sort.Order> orders = new ArrayList<>();
            for (String s : sort) {
                String[] parts = s.split(",", 2);
                String prop = parts[0].trim();
                String dir  = parts.length > 1 ? parts[1].trim() : "asc";
                orders.add(new Sort.Order("desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC, prop));
            }
            springSort = Sort.by(orders);
        }
        Pageable pageable = PageRequest.of(page, size, springSort);
        return service.search(title, year, pageable);
    }


    // ---- mapping DTO -> entité ----
    private Movie toEntity(MovieRequest req, Movie m) {
        Director d = directors.findById(req.directorId)
                .orElseThrow(() -> new IllegalArgumentException("Director not found: " + req.directorId));
        m.setTitle(req.title);
        m.setYear(req.year);
        m.setDurationMinutes(req.durationMinutes);
        m.setDirector(d);

        var aSet = new LinkedHashSet<Actor>();
        if (req.actorIds != null) {
            for (Long id : req.actorIds) {
                aSet.add(actors.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Actor not found: " + id)));
            }
        }
        m.setActors(aSet);

        var gSet = new LinkedHashSet<Genre>();
        if (req.genreIds != null) {
            for (Long id : req.genreIds) {
                gSet.add(genres.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + id)));
            }
        }
        m.setGenres(gSet);

        return m;
    }
}
