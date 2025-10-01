package com.example.SmartBuy.controller;

import com.example.SmartBuy.dto.Usuario.UsuarioDTO;
import com.example.SmartBuy.entities.LoginRequest;
import com.example.SmartBuy.entities.TokenResponse;
import com.example.SmartBuy.security.JwtUtil;
import com.example.SmartBuy.service.LoginService;
import com.example.SmartBuy.service.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticarUsuario(@RequestBody LoginRequest loginRequest) {
        try {

            boolean autenticacao = loginService.autenticaUsuario(loginRequest.getNome(), loginRequest.getSenha());

            if (autenticacao) {
                long tempoToken = 3600000;

                String token =  jwtUtil.gerarToken(loginRequest.getNome(), tempoToken);

                TokenResponse response = new TokenResponse(token);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
            }
        } catch (Exception e) {
            logger.severe("Falha ao logar no sistema!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }

    @GetMapping("/bem-vindo")
    public ResponseEntity<String> mostrarPagina(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        return ResponseEntity.ok("Acesso autorizado à rota protegida");
    }
}
