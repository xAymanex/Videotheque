package com.project.videotheque.service;

import com.project.videotheque.model.Director;
import com.project.videotheque.model.Genre;
import com.project.videotheque.model.Movie;
import com.project.videotheque.repository.MovieRepository;
import com.project.videotheque.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    MovieRepository repo = mock(MovieRepository.class);
    MovieService service = new MovieServiceImpl(repo);

    @Test
    void update_modifiesFields_andSaves() {
        var existing = new Movie(); existing.setId(1L); existing.setTitle("Old");
        when(repo.findById(1L)).thenReturn(Optional.of(existing));

        var director = new Director("Dir");
        var genre    = new Genre("Sci-Fi");
        var incoming = new Movie();
        incoming.setTitle("New"); incoming.setYear(2010); incoming.setDurationMinutes(123);
        incoming.setDirector(director); incoming.setGenre(genre); incoming.setActors(Set.of());

        when(repo.save(any(Movie.class))).thenAnswer(inv -> inv.getArgument(0));

        var result = service.update(1L, incoming);

        ArgumentCaptor<Movie> captor = ArgumentCaptor.forClass(Movie.class);
        verify(repo).save(captor.capture());
        Movie saved = captor.getValue();

        assertThat(saved.getTitle()).isEqualTo("New");
        assertThat(saved.getYear()).isEqualTo(2010);
        assertThat(saved.getDurationMinutes()).isEqualTo(123);
        assertThat(saved.getDirector()).isSameAs(director);
        assertThat(saved.getGenre()).isSameAs(genre);
        assertThat(saved.getActors()).isEmpty();
        assertThat(result).isSameAs(saved);
    }

    @Test
    void update_notFound_throws() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.update(99L, new Movie()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Movie not found");
        verify(repo, never()).save(any());
    }
}
