package com.example.florafauna.controller.V2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.florafauna.assembler.ArticuloModelAssembler;
import com.example.florafauna.service.ArticuloService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.florafauna.model.Articulo;

@RestController
@RequestMapping("/api/v2/articulo")
public class ArticuloControllerV2 {

    @Autowired
    private ArticuloService articuloService;

    @Autowired
    private ArticuloModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los articulos", description = "Muestra todos los artículos de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<Articulo>>> getAllArticulos(){
        List<EntityModel<Articulo>> articulos = articuloService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (articulos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
            articulos,
            linkTo(methodOn(ArticuloControllerV2.class).getAllArticulos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar articulo",description = "Muestra un artículo por su ID")
    public ResponseEntity<EntityModel<Articulo>> getArticuloById(@PathVariable Integer id){
        Articulo articulo = articuloService.findById(id);
        if (articulo == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(assembler.toModel(articulo));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear articulo",description = "Permite crear un articulo")
    public ResponseEntity<EntityModel<Articulo>> createArticulo(@RequestBody Articulo articulo){
        Articulo newArticulo = articuloService.save(articulo);
        return ResponseEntity
            .created(linkTo(methodOn(ArticuloControllerV2.class).getArticuloById(newArticulo.getIdArticulo())).toUri())
            .body(assembler.toModel(newArticulo));
        
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar articulo", description = "Permite actualizar un articulo ya creado")
    public ResponseEntity<EntityModel<Articulo>> updateArticulo(@PathVariable Integer id, @RequestBody Articulo articulo) {
        articulo.setIdArticulo(id);
        Articulo updatedArticulo = articuloService.save(articulo);
        return ResponseEntity.ok(assembler.toModel(updatedArticulo));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un articulo", description = "Permite actualizar una linea de un artículo")
    public ResponseEntity<EntityModel<Articulo>> partialUpdateArticulo(@PathVariable Integer id, @RequestBody Articulo articulo) {
        Articulo updatedArticulo = articuloService.partialUpdate(articulo);
        
        if (updatedArticulo == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedArticulo));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar articulo", description = "Permite borrar un articulo por su ID")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Integer id) {
        Articulo articulo = articuloService.findById(id);
        if (articulo == null) {
            return ResponseEntity.notFound().build();
        }
        
        articuloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
