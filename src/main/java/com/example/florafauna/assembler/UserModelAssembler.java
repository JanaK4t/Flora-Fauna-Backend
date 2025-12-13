package com.example.florafauna.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;

import com.example.florafauna.controller.V2.UserControllerV2;

import com.example.florafauna.model.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User,EntityModel<User>>{

    @Override
    public EntityModel<User> toModel(User user){
        return EntityModel.of(user,
            linkTo(methodOn(UserControllerV2.class).getUserById(user.getIdUser())).withSelfRel(),
            linkTo(methodOn(UserControllerV2.class).getAllUsers()).withRel("users"),
            linkTo(methodOn(UserControllerV2.class).updateUser(user.getIdUser(),user)).withRel("actualizar"),
            linkTo(methodOn(UserControllerV2.class).deleteUser(user.getIdUser())).withRel("eliminar"),
            linkTo(methodOn(UserControllerV2.class).partialUpdateUser(user.getIdUser(), user)).withRel("reemplazar una línea")
        );
    }
    
}