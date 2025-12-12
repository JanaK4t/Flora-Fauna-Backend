package com.example.florafauna.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @JsonProperty("nombreUsuario") 
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", length = 50,nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", length = 50,nullable = false)
    private String contrasena;

    

    @OneToMany
    private List<Articulo> articulos = new ArrayList<>();



//neon
}
