
package com.example.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Producto;
import com.example.demo.repo.productoRepo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Productos", description = "Crear, buscar y administrar productos")
public class ProductoController {

    @Autowired
    productoRepo repo;

    // Obtener todos los productos
    @GetMapping("productos")
    @Operation(summary = "Obtener todos los productos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
    })
    public ResponseEntity<List<Producto>> findAllProductos() {
        return ResponseEntity.ok(repo.findAll());
    }

    // Crear un producto
    @PostMapping("producto")
    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    public ResponseEntity<Producto> addProducto(@RequestBody Producto producto) {

        Producto saved = repo.save(producto);

        URI location = URI.create("/producto/" + saved.getId());

        return ResponseEntity.created(location).body(saved);
    }

    // Buscar un producto por ID
    @GetMapping("producto/{id}")
    @Operation(summary = "Buscar un producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    public ResponseEntity<Producto> getById(@PathVariable String id) {

        Optional<Producto> result = repo.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result.get());
    }

    // Actualizar un producto existente
    @PutMapping("producto/{id}")
    @Operation(summary = "Actualizar un producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    public ResponseEntity<Producto> putProduct(
            @PathVariable String id,
            @RequestBody Producto product) {

        Optional<Producto> result = repo.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id);

        Producto updated = repo.save(product);

        return ResponseEntity.ok(updated);
    }

    // Eliminar un producto
    @DeleteMapping("producto/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró el producto")
    })
    public ResponseEntity<Void> deleteMailbox(@PathVariable("id") String id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
