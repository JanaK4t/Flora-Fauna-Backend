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

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @Column(name = "nombre_usuario", nullable = false)
    private String nombreUsuario;

    @Column(name = "correo", length = 50,nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena",nullable = false)
    private String contrasena;

    @Column(name = "foto", columnDefinition = "TEXT")
    private String foto;

    

    @OneToMany
    private List<Articulo> articulos = new ArrayList<>();



}
