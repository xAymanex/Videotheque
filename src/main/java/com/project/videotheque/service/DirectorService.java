package com.project.videotheque.service;

import com.project.videotheque.model.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorService {
    List<Director> findAll();
    Optional<Director> findById(Long id);
    Director create(Director d);
    Director update(Long id, Director d);
    void delete(Long id);
}
