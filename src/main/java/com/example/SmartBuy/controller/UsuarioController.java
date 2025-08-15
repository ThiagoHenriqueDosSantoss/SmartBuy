package com.example.SmartBuy.controller;


import com.example.SmartBuy.dto.Usuario.CreateUsuarioDTO;
import com.example.SmartBuy.entities.Usuario;
import com.example.SmartBuy.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());

    public UsuarioController(){

    }
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody CreateUsuarioDTO dto){
        try{
            Usuario usuario = usuarioService.criarUsuario(dto);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            logger.severe("Falha ao criar o usuario no controller "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
