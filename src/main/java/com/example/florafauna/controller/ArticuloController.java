package com.example.florafauna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.florafauna.service.ArticuloService;
import com.example.florafauna.model.Articulo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/articulos")
public class ArticuloController {
    @Autowired
    private ArticuloService articuloService;

     @GetMapping
    public ResponseEntity<List<Articulo>> getAllArticulos() {
        List<Articulo> articulos = articuloService.findAll();
        if (articulos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(articulos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> getArticuloById(@PathVariable Integer id) {
        Articulo articulo = articuloService.findById(id);
        if (articulo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articulo);
    }

    @PostMapping
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) {
        Articulo newArticulo = articuloService.save(articulo);
        return ResponseEntity.status(201).body(newArticulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> updateArticulo(@PathVariable Integer id, @RequestBody Articulo articulo) {
        articulo.setIdArticulo(id);
        Articulo updatedArticulo = articuloService.save(articulo);
        return ResponseEntity.ok(updatedArticulo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Articulo> partialUpdateArticulo(@PathVariable Integer id, @RequestBody Articulo articulo) {
        articulo.setIdArticulo(id);
        
        Articulo updatedArticulo = articuloService.partialUpdate(articulo);
        
        if (updatedArticulo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedArticulo);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Integer id) {
        if (articuloService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        
        articuloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
