package com.example.florafauna.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.florafauna.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import jakarta.transaction.Transactional;
import com.example.florafauna.model.User;

@Service
@Transactional
@SuppressWarnings("null")
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public User login(User user) {
    User foundUser = userRepository.findByCorreo(user.getCorreo());
 
    if (foundUser != null &&  passwordEncoder.matches(user.getContrasena(), foundUser.getContrasena())) {
          return foundUser;
     }

     return null;
    }

    public User save(User user) {
    return userRepository.save(user); 
}

    public User partialUpdate(User user){
        User existingUser = userRepository.findById(user.getIdUser()).orElse(null);
        if(existingUser != null){
            if (user.getNombreUsuario() != null){
                existingUser.setNombreUsuario(user.getNombreUsuario());
            }
            if (user.getCorreo() != null){
                existingUser.setCorreo(user.getCorreo());
            }
            if (user.getContrasena() != null){
                existingUser.setContrasena(user.getContrasena());
            }
            if (user.getFoto() != null){
                existingUser.setFoto(user.getFoto());
            }
            
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> findByArticulos_IdArticulo(Integer idArticulo) {
        return userRepository.findByArticulos_IdArticulo(idArticulo);

    }

     public User login(String correo, String contrasenaOg){
        User user = userRepository.findByCorreo(correo);

        if (user == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        if (!passwordEncoder.matches(contrasenaOg, user.getContrasena())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        return user;
    }


}
