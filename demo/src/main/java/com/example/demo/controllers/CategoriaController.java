
package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Department;
import com.example.demo.model.Producto;
import com.example.demo.repo.categoriaRepo;
import com.example.demo.repo.productoRepo;

@RestController
public class CategoriaController {

    @Autowired
    categoriaRepo repo;

    @Autowired
    productoRepo repoProducto;

    @GetMapping("categorias")
    public List<Department> findAllcategoria() {
        return repo.findAll();
    }

    @PostMapping("categoria")
    public Department addcategoria(@RequestBody Department categoria) {
        return repo.save(categoria);
    }

    @PostMapping("addProductoCategoria/{productId}/{categoriaId}")
    public ResponseEntity<Department> addcategoria(
            @PathVariable String productId,
            @PathVariable String categoriaId) {

        Optional<Producto> p = repoProducto.findById(productId);
        Optional<Department> d = repo.findById(categoriaId);

        if (p.isEmpty() || d.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        d.get().getProductos().add(p.get());

        return ResponseEntity.ok(repo.save(d.get()));
    }

    @GetMapping("categoria/producto/{productId}")
    public ResponseEntity<Set<Department>> getDepartmentsByProduct(
            @PathVariable String productId) {

        Optional<Producto> p = repoProducto.findById(productId);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(p.get().getDeparments());
    }

    @GetMapping("categoria/{id}")
    public ResponseEntity<Department> getById(@PathVariable String id) {

        Optional<Department> categoria = repo.findById(id);

        if (categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria.get());
    }

    @PutMapping("categoria/{id}")
    public ResponseEntity<Department> putDepartment(
            @PathVariable String id,
            @RequestBody Department categoria) {

        Optional<Department> existente = repo.findById(id);

        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        categoria.setId(id);

        return ResponseEntity.ok(repo.save(categoria));
    }

    @DeleteMapping("categoria/{id}")
    public ResponseEntity<Void> deleteDeparment(@PathVariable String id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
