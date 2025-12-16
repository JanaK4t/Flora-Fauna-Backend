package com.example.florafauna.controller.V1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

import com.example.florafauna.service.ArticuloService;
import com.example.florafauna.model.Articulo;
import com.example.florafauna.model.User;
import com.example.florafauna.repository.ArticuloRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/articulos")
public class ArticuloController {
    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloRepository articuloRepository;

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

    @PutMapping("/update-photo")
    public ResponseEntity<?> updatePhoto(@RequestBody Map<String, String> payload) {
        String nombre = payload.get("nombre");
        String nuevaFoto = payload.get("imagenUri");

        Articulo articulo = articuloRepository.findByNombre(nombre);

        if (articulo != null) {
            articulo.setImagenUri(nuevaFoto);
            articuloRepository.save(articulo);
            return ResponseEntity.ok(articulo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Articulo no encontrado");
        }
    }
}
