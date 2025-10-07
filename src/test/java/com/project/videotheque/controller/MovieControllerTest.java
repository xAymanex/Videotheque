package com.project.videotheque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.videotheque.model.*;
import com.project.videotheque.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
class MovieControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockBean MovieService service; // on mocke le service

    @Test
    void search_returnsPagedMovies() throws Exception {
        var dir = new Director("Dir"); dir.setId(1L);
        var gen = new Genre("Sci-Fi"); gen.setId(1L);

        var m = new Movie();
        m.setId(42L); m.setTitle("Alpha"); m.setYear(2010); m.setDurationMinutes(100);
        m.setDirector(dir); m.setGenre(gen); m.setActors(Set.of());

        Page<Movie> page = new PageImpl<>(List.of(m), PageRequest.of(0,1, Sort.by("title")), 1);
        when(service.search("a", null, PageRequest.of(0,1, Sort.by("title")))).thenReturn(page);

        mvc.perform(get("/api/movies/search")
                        .param("title","a")
                        .param("page","0")
                        .param("size","1")
                        .param("sort","title,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Alpha"))
                .andExpect(jsonPath("$.content[0].director.name").value("Dir"))
                .andExpect(jsonPath("$.content[0].genre.name").value("Sci-Fi"));
    }
}
