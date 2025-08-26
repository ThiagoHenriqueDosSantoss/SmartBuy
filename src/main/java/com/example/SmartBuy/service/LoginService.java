package com.example.SmartBuy.service;


import com.example.SmartBuy.entities.Usuario;
import com.example.SmartBuy.repository.UsuarioRepository;
import com.example.SmartBuy.security.CriptografiaUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    public LoginService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public boolean autenticaUsuario(String nome, String senha){
        try{
            Optional<Usuario> autenticação = usuarioRepository.findByNome(nome);

            if(autenticação.isPresent()){
                Usuario usuario = autenticação.get();

                if (CriptografiaUtil.verificarSenha(senha, usuario.getSenha())) {
                    return true;
                } else {
                    logger.warning("Senha incorreta para o usuário: " + nome);
                }
            }
            return true;
        } catch (Exception e) {
            logger.severe("Falha para autenticar usuario no login services!" + e.getMessage());
        }
        return false;
    }
}
