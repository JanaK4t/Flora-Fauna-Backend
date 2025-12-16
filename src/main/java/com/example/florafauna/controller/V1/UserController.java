package com.example.florafauna.controller.V1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.florafauna.service.UserService;
import com.example.florafauna.dto.LoginRequest;
import com.example.florafauna.model.User;
import com.example.florafauna.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/update-photo")
    public ResponseEntity<?> updatePhoto(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");
        String nuevaFoto = payload.get("foto");

        User user = userRepository.findByCorreo(correo);

        if (user != null) {
            user.setFoto(nuevaFoto);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> partialUpdateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setIdUser(id);
        User updatedUser = userService.partialUpdate(user);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    
    return ResponseEntity.noContent().build();

}

    @GetMapping("/articulos/{articuloId}")
    public ResponseEntity<List<User>> findByArticulos_IdArticulo(@PathVariable Integer idArticulo) {
        List<User> users = userService.findByArticulos_IdArticulo(idArticulo);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    User user = userRepository.findByCorreo(request.getCorreo());

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (user != null && encoder.matches(request.getContrasena(), user.getContrasena())) {
        
        return ResponseEntity.ok(user);
        
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
}
}

   



