package com.project.videotheque.repository;

import com.project.videotheque.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MovieRepositoryTest {

    @Autowired DirectorRepository directors;
    @Autowired ActorRepository actors;
    @Autowired GenreRepository genres;
    @Autowired MovieRepository movies;

    Long nolanId, leoId, scifiId;

    @BeforeEach
    void setup() {
        Director nolan = directors.save(new Director("Christopher Nolan"));
        nolanId = nolan.getId();
        Actor leo = actors.save(new Actor("Leonardo DiCaprio"));
        leoId = leo.getId();
        Genre scifi = genres.save(new Genre("Sci-Fi"));
        scifiId = scifi.getId();

        Movie inception = new Movie();
        inception.setTitle("Inception");
        inception.setYear(2010);
        inception.setDurationMinutes(148);
        inception.setDirector(nolan);
        inception.setActors(Set.of(leo));
        inception.setGenres(Set.of(scifi));
        movies.save(inception);

        Movie interstellar = new Movie();
        interstellar.setTitle("Interstellar");
        interstellar.setYear(2014);
        interstellar.setDurationMinutes(169);
        interstellar.setDirector(nolan);
        interstellar.setGenres(Set.of(scifi));
        movies.save(interstellar);
    }

    @Test
    void search_byTitle_shouldReturnMatch() {
        Page<Movie> page = movies.search("inc", null, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    void search_byYear_shouldReturnMatch() {
        Page<Movie> page = movies.search(null, 2014, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Interstellar");
    }
}
