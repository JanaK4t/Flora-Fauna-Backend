package com.example.florafauna.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.florafauna.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    
    User findByCorreo(String correo);
List<User> findByArticulos_IdArticulo(Integer idArticulo);
}
