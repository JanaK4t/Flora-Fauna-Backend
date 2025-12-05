package com.example.florafauna.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import jakarta.persistence.Table;
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

    @Column(name = "nombreUsuario", length = 20,nullable = false, unique = true)
    private String nombre;

    @Column(name = "correo", length = 50,nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", length = 50,nullable = false)
    private String contrasena;

    @OneToMany
    private List<Articulo> articulos = new ArrayList<>();



//neon
}
