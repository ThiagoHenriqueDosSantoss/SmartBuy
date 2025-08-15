package com.example.SmartBuy.service;

import com.example.SmartBuy.dto.Usuario.CreateUsuarioDTO;
import com.example.SmartBuy.entities.Usuario;
import com.example.SmartBuy.enums.UsuarioEnum;
import com.example.SmartBuy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = Logger.getLogger(UsuarioService.class.getName());

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(CreateUsuarioDTO dto) {
        try {
            Usuario usuario = new Usuario();

            if (dto.getNome() == null || dto.getNome().isBlank()) {
                throw new Exception("O campo nome é obrigatório preencher!");
            }
            usuario.setNome(dto.getNome());

            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new Exception("O campo email é obrigatório preencher!");
            }
            usuario.setEmail(dto.getEmail());

            if (dto.getSenha() == null || dto.getSenha().isBlank()) {
                throw new Exception("O campo senha é obrigatório preencher!");
            }
            usuario.setSenha(dto.getSenha());

            if (dto.getTipo() == null) {
                throw new Exception("Por favor informe um tipo de pessoa!");
            }


            if (dto.getTipo() == UsuarioEnum.FISICA) {
                if (dto.getCpf() == null || dto.getCpf().isBlank()) {
                    throw new Exception("CPF é obrigatório para pessoa física.");
                }
                usuario.setCpf(dto.getCpf());
            } else if (dto.getTipo() == UsuarioEnum.JURIDICA) {
                if (dto.getCnpj() == null || dto.getCnpj().isBlank()) {
                    throw new Exception("CNPJ é obrigatório para pessoa jurídica.");
                }
                usuario.setCnpj(dto.getCnpj());
            } else {
                throw new Exception("Tipo de pessoa inválido. Use FISICA ou JURIDICA.");
            }

            usuario.setTipo(dto.getTipo()); // seta o enum direto
            usuario.setDataCriacao(LocalDateTime.now());

            return usuarioRepository.save(usuario);

        } catch (Exception e) {
            logger.severe("Erro para criar o usuario no services: " + e.getMessage());
            return null;
        }
    }

}
