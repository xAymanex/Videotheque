package com.project.videotheque.controller;

import com.project.videotheque.dto.MovieRequest;
import com.project.videotheque.model.Actor;
import com.project.videotheque.model.Director;
import com.project.videotheque.model.Genre;
import com.project.videotheque.model.Movie;
import com.project.videotheque.repository.ActorRepository;
import com.project.videotheque.repository.DirectorRepository;
import com.project.videotheque.repository.GenreRepository;
import com.project.videotheque.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService service;
    private final DirectorRepository directors;
    private final ActorRepository actors;
    private final GenreRepository genres;

    public MovieController(MovieService service, DirectorRepository directors,
                           ActorRepository actors, GenreRepository genres) {
        this.service = service; this.directors = directors; this.actors = actors; this.genres = genres;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody @Valid MovieRequest req) {
        Movie m = toEntity(req, new Movie());
        Movie saved = service.create(m);
        return ResponseEntity.created(URI.create("/api/movies/" + saved.getId())).body(saved);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Movie> list() { return service.findAll(); }

    // UPDATE
    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody @Valid MovieRequest req) {
        Movie m = toEntity(req, service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id)));
        return service.update(id, m);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH with manual sort parsing "prop,dir"
    @GetMapping("/search")
    public Page<Movie> search(@RequestParam(required = false) String title,
                              @RequestParam(required = false) Integer year,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(required = false) List<String> sort) {
        Sort springSort = Sort.unsorted();
        if (sort != null && !sort.isEmpty()) {
            List<Sort.Order> orders = new ArrayList<>();
            for (String s : sort) {
                if (s == null || s.isBlank()) continue;
                String[] parts = s.split(",", 2);
                String prop = parts[0].trim();
                String dir  = parts.length > 1 ? parts[1].trim() : "asc";
                Sort.Direction direction = "desc".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;
                if (!List.of("id","title","year","durationMinutes").contains(prop))
                    throw new IllegalArgumentException("Unknown sort property: " + prop);
                orders.add(new Sort.Order(direction, prop));
            }
            if (!orders.isEmpty()) springSort = Sort.by(orders);
        }
        return service.search(title, year, PageRequest.of(page, size, springSort));
    }

    // mapping DTO -> Entity
    private Movie toEntity(MovieRequest req, Movie m) {
        Director d = directors.findById(req.directorId)
                .orElseThrow(() -> new IllegalArgumentException("Director not found: " + req.directorId));
        Genre g = genres.findById(req.genreId)
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: " + req.genreId));

        m.setTitle(req.title);
        m.setYear(req.year);
        m.setDurationMinutes(req.durationMinutes);
        m.setDirector(d);
        m.setGenre(g);

        var aSet = new LinkedHashSet<Actor>();
        if (req.actorIds != null) {
            for (Long id : req.actorIds) {
                aSet.add(actors.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Actor not found: " + id)));
            }
        }
        m.setActors(aSet);
        return m;
    }
}
