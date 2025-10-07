package com.project.videotheque.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.videotheque.model.Director;
import com.project.videotheque.repository.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerValidationTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @Autowired DirectorRepository directors;

    Long directorId;

    @BeforeEach
    void setup() {
        directorId = directors.findByName("Christopher Nolan")
                .orElseGet(() -> directors.save(new Director("Christopher Nolan")))
                .getId();
    }

    @Test
    void post_missingTitle_shouldReturn400() throws Exception {
        var payload = Map.of(
                "year", 2010,
                "durationMinutes", 148,
                "directorId", directorId
        );
        mvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_unknownDirector_shouldReturn400() throws Exception {
        var payload = Map.of(
                "title", "Inception",
                "year", 2010,
                "durationMinutes", 148,
                "directorId", 99999
        );
        mvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
