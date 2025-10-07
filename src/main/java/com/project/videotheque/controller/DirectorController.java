package com.project.videotheque.controller;

import com.project.videotheque.model.Director;
import com.project.videotheque.service.DirectorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/directors")
public class DirectorController {
    private final DirectorService service;
    public DirectorController(DirectorService service) { this.service = service; }

    @GetMapping
    public List<Director> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> byId(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Director> create(@RequestBody @Valid Director d) {
        var saved = service.create(d);
        return ResponseEntity.created(URI.create("/api/directors/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Director update(@PathVariable Long id, @RequestBody @Valid Director d) {
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
