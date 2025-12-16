package com.example.florafauna.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArticulo;

    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "descripcion",nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "imagen_uri",columnDefinition = "TEXT")
    private String imagenUri;

    
    
}
