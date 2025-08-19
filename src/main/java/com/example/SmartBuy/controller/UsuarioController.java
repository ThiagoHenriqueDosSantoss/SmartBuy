package com.example.SmartBuy.controller;


import com.example.SmartBuy.dto.Usuario.UsuarioDTO;
import com.example.SmartBuy.entities.Usuario;
import com.example.SmartBuy.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO dto){
        try{
            Usuario usuario = usuarioService.criarUsuario(dto);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            logger.severe("Falha ao criar o usuario no controller "+ e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping(value = "/usuario/{idUsuario}")
    public ResponseEntity<Boolean> deletaUsuario(@PathVariable("idUsuario") long idUsuario){
        try{
            boolean response = usuarioService.deletarUsuario(idUsuario);

            if(response == true){
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            logger.severe("Erro ao deletar usuario no controller" +e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PatchMapping(value = "/usuario/{idUsuario}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable("idUsuario") long idUsuario,@RequestBody UsuarioDTO dto){
        try{
            Usuario response = usuarioService.atualizarUsuario(idUsuario,dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.severe("Erro ao deletar usuario no controller" +e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping(value = "/usuario")
    public ResponseEntity<List<Usuario>> listarUsuario(){
        try{
            List<Usuario> listaUsuario = usuarioService.listarUsuarios();
            return ResponseEntity.ok(listaUsuario);
        } catch (Exception e) {
            logger.severe("Erro ao listar usuarios no controller!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
