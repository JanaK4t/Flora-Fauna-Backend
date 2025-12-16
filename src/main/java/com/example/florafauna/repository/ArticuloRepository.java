package com.example.florafauna.repository;

import org.springframework.stereotype.Repository;
import com.example.florafauna.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.florafauna.model.Categoria;



@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer>{
    
    List<Articulo> findByCategoria(Categoria categoria);
    Articulo findByNombre(String nombre);
}
