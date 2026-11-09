
package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Comment;
import com.example.demo.model.Producto;
import com.example.demo.repo.comentarioRepo;
import com.example.demo.repo.productoRepo;

@RestController
public class ComentarioController {

    @Autowired
    comentarioRepo repo;

    @Autowired
    productoRepo repoProducto;

    @GetMapping("comentarios")
    public List<Comment> findAllcomentarios() {
        return repo.findAll();
    }

    @PostMapping("comentario")
    public Comment addcomentario(@RequestBody Comment comentario) {
        return repo.save(comentario);
    }

    @PostMapping("comentario/{productId}")
    public ResponseEntity<Comment> addcomentario(
            @RequestBody Comment comentario,
            @PathVariable String productId) {

        Optional<Producto> p = repoProducto.findById(productId);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        comentario.setProducto(p.get());

        return ResponseEntity.ok(repo.save(comentario));
    }

    @GetMapping("comentarios/{productId}")
    public ResponseEntity<Set<Comment>> getCommentsByProduct(
            @PathVariable String productId) {

        Optional<Producto> p = repoProducto.findById(productId);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(p.get().getComentarios());
    }

    @GetMapping("comentario/{id}")
    public ResponseEntity<Comment> getById(@PathVariable String id) {

        Optional<Comment> comentario = repo.findById(id);

        if (comentario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comentario.get());
    }

    @PutMapping("comentario/{id}")
    public ResponseEntity<Comment> putusuari(
            @PathVariable String id,
            @RequestBody Comment comentario) {

        Optional<Comment> existente = repo.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        comentario.setId(id);

        return ResponseEntity.ok(repo.save(comentario));
    }

    @DeleteMapping("comentario/{id}")
    public ResponseEntity<Void> deleteMailbox(@PathVariable String id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
