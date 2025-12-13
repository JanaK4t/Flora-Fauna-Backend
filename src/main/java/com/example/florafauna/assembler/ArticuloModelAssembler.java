package com.example.florafauna.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;

import com.example.florafauna.controller.V2.ArticuloControllerV2;

import com.example.florafauna.model.Articulo;

@Component
public class ArticuloModelAssembler implements RepresentationModelAssembler<Articulo,EntityModel<Articulo>>{

    @Override
    public EntityModel<Articulo> toModel(Articulo articulo){
        return EntityModel.of(articulo,
            linkTo(methodOn(ArticuloControllerV2.class).getArticuloById(articulo.getIdArticulo())).withSelfRel(),
            linkTo(methodOn(ArticuloControllerV2.class).getAllArticulos()).withRel("articulos"),
            linkTo(methodOn(ArticuloControllerV2.class).updateArticulo(articulo.getIdArticulo(),articulo)).withRel("actualizar"),
            linkTo(methodOn(ArticuloControllerV2.class).deleteArticulo(articulo.getIdArticulo())).withRel("eliminar"),
            linkTo(methodOn(ArticuloControllerV2.class).partialUpdateArticulo(articulo.getIdArticulo(), articulo)).withRel("reemplazar una línea")
        );
    }
    
}
