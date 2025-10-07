package com.project.videotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max = 200)
    @Column(nullable=false)
    private String title;

    @Min(1888) @Max(2100)
    @Column(name = "release_year")
    private int year;

    @Positive
    private int durationMinutes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Director director;

    // === Nouveau : un film a 1 genre ===
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Genre genre;

    // === On garde ManyToMany pour les acteurs ===
    @ManyToMany
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors = new LinkedHashSet<>();



    public Movie() {}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    public Director getDirector() { return director; }
    public void setDirector(Director director) { this.director = director; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Set<Actor> getActors() { return actors; }
    public void setActors(Set<Actor> actors) { this.actors = actors; }
}
