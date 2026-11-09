
package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Producto;
import com.example.demo.model.Image;
import com.example.demo.repo.imagenRepo;
import com.example.demo.repo.productoRepo;

@RestController
public class ImagenController {

    @Autowired
    imagenRepo repo;

    @Autowired
    productoRepo repoProducto;

    @GetMapping("imagenes")
    public List<Image> findAllimagenes() {
        return repo.findAll();
    }

    @PostMapping("imagen")
    public Image addimagen(@RequestBody Image imagen) {
        return repo.save(imagen);
    }

    @PostMapping("imagen/{productId}")
    public ResponseEntity<Image> addimagen(
            @RequestBody Image imagen,
            @PathVariable String productId) {

        Optional<Producto> p = repoProducto.findById(productId);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        imagen.setProducto(p.get());

        return ResponseEntity.ok(repo.save(imagen));
    }

    // Consultar imágenes asociadas a un producto
    @GetMapping("imagenes/producto/{productId}")
    public ResponseEntity<Set<Image>> getImagensByProduct(
            @PathVariable String productId) {

        Optional<Producto> p = repoProducto.findById(productId);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(p.get().getImagenes());
    }

    // Consultar una imagen por su ID
    @GetMapping("imagen/{id}")
    public ResponseEntity<Image> getById(@PathVariable String id) {

        Optional<Image> imagen = repo.findById(id);

        if (imagen.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(imagen.get());
    }

    @PutMapping("imagen/{id}")
    public ResponseEntity<Image> putusuari(
            @PathVariable String id,
            @RequestBody Image imagen) {

        Optional<Image> existente = repo.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        imagen.setId(id);

        return ResponseEntity.ok(repo.save(imagen));
    }

    @DeleteMapping("imagen/{id}")
    public ResponseEntity<Void> deleteMailbox(@PathVariable String id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
