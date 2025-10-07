package com.project.videotheque.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity
public class Genre {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    // === Nouveau : un genre possède plusieurs films ===
    @OneToMany(mappedBy = "genre", orphanRemoval = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Set<Movie> movies = new LinkedHashSet<>();

    public Genre() {}
    public Genre(String name) { this.name = name; }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }
}
