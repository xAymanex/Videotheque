package com.project.videotheque.service.impl;

import com.project.videotheque.model.Genre;
import com.project.videotheque.repository.GenreRepository;
import com.project.videotheque.service.GenreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service @Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository repo;
    public GenreServiceImpl(GenreRepository repo){ this.repo = repo; }

    public List<Genre> findAll(){ return repo.findAll(); }
    public Optional<Genre> findById(Long id){ return repo.findById(id); }
    public Genre create(Genre g){
        repo.findByName(g.getName()).ifPresent(x -> { throw new IllegalArgumentException("Genre already exists"); });
        return repo.save(g);
    }
    public Genre update(Long id, Genre g){
        return repo.findById(id).map(e -> { e.setName(g.getName()); return repo.save(e); })
                .orElseThrow(() -> new IllegalArgumentException("Genre not found: "+id));
    }
    public void delete(Long id){ repo.deleteById(id); }
}
