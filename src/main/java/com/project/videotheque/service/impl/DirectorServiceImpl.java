package com.project.videotheque.service.impl;

import com.project.videotheque.model.Director;
import com.project.videotheque.repository.DirectorRepository;
import com.project.videotheque.service.DirectorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository repo;
    public DirectorServiceImpl(DirectorRepository repo) { this.repo = repo; }

    @Override public List<Director> findAll() { return repo.findAll(); }
    @Override public Optional<Director> findById(Long id) { return repo.findById(id); }
    @Override public Director create(Director d) { return repo.save(d); }
    @Override public Director update(Long id, Director d) {
        return repo.findById(id).map(existing -> {
            existing.setName(d.getName());
            return repo.save(existing);
        }).orElseThrow(() -> new IllegalArgumentException("Director not found: " + id));
    }
    @Override public void delete(Long id) { repo.deleteById(id); }
}
