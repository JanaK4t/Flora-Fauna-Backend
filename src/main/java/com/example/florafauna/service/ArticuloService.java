package com.example.florafauna.service;

import org.springframework.stereotype.Service;

import com.example.florafauna.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.florafauna.model.Articulo;
import com.example.florafauna.model.Categoria;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@SuppressWarnings("null")
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    public List<Articulo> findAll(){
        return articuloRepository.findAll();
    }

    public Articulo findById(Integer id){
        Articulo articulo = articuloRepository.findById(id).orElse(null);
        return articulo;
    }

    public Articulo save(Articulo articulo){
        return articuloRepository.save(articulo);
    }

    public Articulo partialUpdate(Articulo articulo){
        Articulo existingArticulo = articuloRepository.findById(articulo.getIdArticulo()).orElse(null);
        if (existingArticulo != null){
            if (articulo.getNombre() != null){
                existingArticulo.setNombre(articulo.getNombre());
            }
            if (articulo.getDescripcion() != null){
                existingArticulo.setDescripcion(articulo.getDescripcion());
            }
            if (articulo.getCategoria() != null){
                existingArticulo.setCategoria(articulo.getCategoria());
            }
            if (articulo.getImagenUri() != null){
                existingArticulo.setImagenUri(articulo.getImagenUri());
            }
            
            return articuloRepository.save(existingArticulo);
        }
        return null;
    }

    public void deleteById(Integer id){
        articuloRepository.deleteById(id);
    }

    public List<Articulo> findByCategoria(Categoria categoria){
        return articuloRepository.findByCategoria(categoria);
    }

    public List<Articulo> buscarPorCategoria(Categoria categoria){
        return articuloRepository.findByCategoria(categoria);
    }
    
}
