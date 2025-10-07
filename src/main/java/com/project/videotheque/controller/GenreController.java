package com.project.videotheque.controller;

import com.project.videotheque.model.Genre;
import com.project.videotheque.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService service;
    public GenreController(GenreService service){ this.service = service; }

    @GetMapping public List<Genre> all(){ return service.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Genre> byId(@PathVariable Long id){
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public ResponseEntity<Genre> create(@RequestBody @Valid Genre g){
        var saved = service.create(g);
        return ResponseEntity.created(URI.create("/api/genres/"+saved.getId())).body(saved);
    }
    @PutMapping("/{id}") public Genre update(@PathVariable Long id, @RequestBody @Valid Genre g){ return service.update(id,g); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){ service.delete(id); return ResponseEntity.noContent().build(); }
}
