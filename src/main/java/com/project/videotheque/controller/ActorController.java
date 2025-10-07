package com.project.videotheque.controller;

import com.project.videotheque.model.Actor;
import com.project.videotheque.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {
    private final ActorService service;
    public ActorController(ActorService service){ this.service = service; }

    @GetMapping public List<Actor> all(){ return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Actor> byId(@PathVariable Long id){
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public ResponseEntity<Actor> create(@RequestBody @Valid Actor a){
        var saved = service.create(a);
        return ResponseEntity.created(URI.create("/api/actors/"+saved.getId())).body(saved);
    }
    @PutMapping("/{id}") public Actor update(@PathVariable Long id, @RequestBody @Valid Actor a){ return service.update(id,a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }
}
