package com.project.videotheque.service.impl;

import com.project.videotheque.model.Actor;
import com.project.videotheque.repository.ActorRepository;
import com.project.videotheque.service.ActorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service @Transactional
public class ActorServiceImpl implements ActorService {
    private final ActorRepository repo;
    public ActorServiceImpl(ActorRepository repo) { this.repo = repo; }

    public List<Actor> findAll(){ return repo.findAll(); }
    public Optional<Actor> findById(Long id){ return repo.findById(id); }
    public Actor create(Actor a){
        repo.findByName(a.getName()).ifPresent(x -> { throw new IllegalArgumentException("Actor already exists"); });
        return repo.save(a);
    }
    public Actor update(Long id, Actor a){
        return repo.findById(id).map(e -> { e.setName(a.getName()); return repo.save(e); })
                .orElseThrow(() -> new IllegalArgumentException("Actor not found: "+id));
    }
    public void delete(Long id){ repo.deleteById(id); }
}
