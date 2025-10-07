package com.project.videotheque.service;

import com.project.videotheque.model.Genre;
import java.util.*;

public interface GenreService {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Genre create(Genre g);
    Genre update(Long id, Genre g);
    void delete(Long id);
}
