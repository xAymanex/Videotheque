package com.project.videotheque.service;

import com.project.videotheque.model.Actor;
import java.util.*;

public interface ActorService {
    List<Actor> findAll();
    Optional<Actor> findById(Long id);
    Actor create(Actor a);
    Actor update(Long id, Actor a);
    void delete(Long id);
}
