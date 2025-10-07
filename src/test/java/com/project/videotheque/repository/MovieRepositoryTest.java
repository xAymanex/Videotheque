package com.project.videotheque.repository;

import com.project.videotheque.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // utilise TA base fichier
@AutoConfigureTestEntityManager
@Sql(scripts = "classpath:test-data/reset-and-seed.sql") // état connu avant chaque test
class MovieRepositoryTest {

    @Autowired MovieRepository movies;

    @Test
    void search_byTitle_shouldFindInception() {
        Page<Movie> page = movies.search("inc", null, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    void search_byYear_shouldFindInterstellar() {
        Page<Movie> page = movies.search(null, 2014, PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent().get(0).getTitle()).isEqualTo("Interstellar");
    }
}
