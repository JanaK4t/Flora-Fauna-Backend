package com.example.florafauna.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.florafauna.assembler.UserModelAssembler;
import com.example.florafauna.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.florafauna.controller.V2.UserControllerV2;
import com.example.florafauna.dto.LoginRequest;
import com.example.florafauna.model.User;
import com.example.florafauna.repository.UserRepository;

@RestController
@RequestMapping("/api/v2/users")
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los usuarios", description = "Muestra todos los usuarios de la base de datos")
    public ResponseEntity<CollectionModel<EntityModel<User>>> getAllUsers() {
        List<EntityModel<User>> users = userService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
       
        return ResponseEntity.ok(CollectionModel.of(
            users,
            linkTo(methodOn(UserControllerV2.class).getAllUsers()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Mostrar usuario", description = "Muestra un usuario por ID")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(user));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear usuario",description = "Permite crear un usuario")
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        User newUser = userService.save(user);
        return ResponseEntity
            .created(linkTo(methodOn(UserControllerV2.class).getUserById(newUser.getIdUser())).toUri())
            .body(assembler.toModel(newUser));
    }

    @PutMapping(value = "/{id}",produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar usuario", description = "Permite actualizar un usuario ya creado")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setIdUser(id);
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @PatchMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Cambiar un dato de un usuario", description = "Permite actualizar una linea de un usuario")
    public ResponseEntity<EntityModel<User>> partialUpdateUser(@PathVariable Integer id, @RequestBody User user) {
        User updatedUser = userService.partialUpdate(user);
        
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(updatedUser));
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Borrar usuario", description = "Permite borrar un usuario por su ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByCorreo(request.getCorreo());

        if (user != null && user.getContrasena().equals(request.getContrasena())) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }








    
}
