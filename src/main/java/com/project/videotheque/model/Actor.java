package com.project.videotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity
public class Actor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false, unique = true)
    private String name;

    // côté inverse du ManyToMany
    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    @com.fasterxml.jackson.annotation.JsonIgnore // évite la récursion JSON
    private Set<Movie> movies = new LinkedHashSet<>();

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }


    public Actor() {}
    public Actor(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
